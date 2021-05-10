package gui.controllers;

import data.FileAccess;
import data.Recording;
import data.AutoTrackData;
import gui.popups.action.treeItemAction.AddToProjectPopup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.treeItems.TaskTreeItem;

import java.time.LocalTime;
import java.util.*;

public class AutotrackTabController {


    @FXML
    private Button autotrackButton;

    @FXML
    private TableColumn<AutoTrackData, String> pathColumn;

    @FXML
    private TableColumn<AutoTrackData, Integer> timeColumn;

    @FXML
    private TableView<AutoTrackData> autoTable;

    private Map<String, AutoTrackData> baseList = new HashMap<>();

    private Map<String, AutoTrackData> newList;

    ObservableList<AutoTrackData> helper = FXCollections.observableArrayList();

    private MainController mainController;

    private HistoryTabController historyTabController;



    @FXML
    public void initialize() {
    }

    private void configureColumns() {
        pathColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }


    public void loadProcesses() {
        newList = new HashMap<>();
        Optional<String> currUser = ProcessHandle.current().info().user();
        configureColumns();
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
    // TODO Wont save time after adding record
    public void showContextMenu()  {
        AutoTrackData selectedItem = autoTable.getSelectionModel().getSelectedItem();
        ContextMenu contextMenu = new ContextMenu();
        MenuItem addToProjectMenuItem = new MenuItem("Add to project");
        contextMenu.getItems().add(addToProjectMenuItem);
        autoTable.setContextMenu(contextMenu);
        addToProjectMenuItem.setOnAction(event -> {
            AddToProjectPopup popup = new AddToProjectPopup(selectedItem);
            popup.popup();
            TaskTreeItem task = popup.addRecord();
            var recording = new Recording(task, selectedItem.calculateDuration());
            recording.setRecordEnd(selectedItem.getInitialDate());
            mainController.addToHistory(recording);
            FileAccess.saveData();
            historyTabController.showByTime(Integer.MAX_VALUE);
        });
    }

    public void init(MainController mainController) {
        this.mainController = mainController;
        historyTabController = mainController.getHistoryTabController();
    }

}


