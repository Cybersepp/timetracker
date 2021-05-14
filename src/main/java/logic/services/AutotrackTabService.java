package logic.services;

import data.AutoTrackData;
import gui.controllers.HistoryTabController;
import gui.controllers.MainController;
import gui.popups.action.treeItemAction.AddRecordingToProjectPopup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalTime;
import java.util.*;

public class AutotrackTabService implements Service{

    private TableView<AutoTrackData> autoTable;
    private Map<String, AutoTrackData> newList;
    private Map<String, AutoTrackData> baseList = new HashMap<>();
    ObservableList<AutoTrackData> helper = FXCollections.observableArrayList();

    public AutotrackTabService(TableView<AutoTrackData> autoTable) {
        this.autoTable = autoTable;
    }

    // TODO Wont save time after adding record
    public void showContextMenu(MainController mainController, HistoryTabController historyTabController)  {
        AutoTrackData selectedItem = autoTable.getSelectionModel().getSelectedItem();
        var contextMenu = new ContextMenu();
        var addToProjectMenuItem = new MenuItem("Add to project");
        contextMenu.getItems().add(addToProjectMenuItem);
        autoTable.setContextMenu(contextMenu);
        addToProjectMenuItem.setOnAction(event -> {
            new AddRecordingToProjectPopup(selectedItem, mainController, autoTable).popup();
            historyTabController.showByTime(Integer.MAX_VALUE, mainController.getGraphTabController());
        });
    }

    public void loadProcesses(TableColumn<AutoTrackData, String> pathColumn, TableColumn<AutoTrackData, Integer> timeColumn) {
        newList = new HashMap<>();
        Optional<String> currUser = ProcessHandle.current().info().user();
        configureColumns(pathColumn, timeColumn);
        ProcessHandle.allProcesses()
                .filter(p1 -> p1.info().user().equals(currUser))
                .forEach(p2 -> showProcess(p2, newList));

        if (baseList.isEmpty()) {
            baseList.putAll(newList);
        }
        Map<String, AutoTrackData> compareMap = new HashMap<>(baseList);
        for (String oldKey : baseList.keySet()) {
            for (String newKey : newList.keySet()) {
                if (oldKey.equals(newKey)) {
                    compareMap.remove(oldKey);
                }
            }
        }
        if (compareMap.isEmpty()) {
            baseList = newList;
            return;
        }
        List<AutoTrackData> conversionList = new ArrayList<>(compareMap.values());
        helper.addAll(conversionList);
        autoTable.setItems(helper);
        baseList = newList;
    }


    private void showProcess(ProcessHandle ph, Map<String, AutoTrackData> newList) {
        ProcessHandle.Info info = ph.info();
        if (newList.containsKey(info.command().toString())) {
            LocalTime duration = newList.get(info.command().toString()).getDuration();
            if (info.totalCpuDuration().isPresent()) {
                duration = duration.plusSeconds(info.totalCpuDuration().get().toSeconds());
                duration = duration.plusMinutes(info.totalCpuDuration().get().toMinutes());
                duration = duration.plusHours(info.totalCpuDuration().get().toHours());
                newList.get(info.command().toString()).setDuration(duration);
            }
        } else {
            LocalTime duration = LocalTime.of(info.totalCpuDuration().get().toHoursPart(),
                    info.totalCpuDuration().get().toMinutesPart(), info.totalCpuDuration().get().toSecondsPart());
            newList.put(info.command().toString(), new AutoTrackData(info.command().orElse("none"), duration));
        }
    }

    private void configureColumns(TableColumn<AutoTrackData, String> pathColumn, TableColumn<AutoTrackData, Integer> timeColumn) {
        pathColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }
}
