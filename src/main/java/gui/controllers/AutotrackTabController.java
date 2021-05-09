package gui.controllers;

import data.tableview.AutoTrackData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class AutotrackTabController {


    @FXML
    private Button autotrackButton;

    @FXML
    private TableColumn<AutoTrackData, String> pathColumn;

    @FXML
    private TableColumn<AutoTrackData, String> timeColumn;

    @FXML
    private TableView<AutoTrackData> autoTable;

    private Map<String, AutoTrackData> baseList = new HashMap<>();

    private Map<String, AutoTrackData> newList;

    ObservableList<AutoTrackData> helper = FXCollections.observableArrayList();

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");


    @FXML
    public void initialize() {
    }

    public void configureColumns() {
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
        Map<String, AutoTrackData> compareMap = new HashMap<>();
        compareMap.putAll(baseList);
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


    static void showProcess(ProcessHandle ph, Map<String, AutoTrackData> newList) {
        ProcessHandle.Info info = ph.info();
        if (newList.containsKey(info.command().toString())) {
            LocalTime duration = newList.get(info.command().toString()).getDuration();
            duration = duration.plusSeconds(info.totalCpuDuration().get().toSeconds());
            duration = duration.plusMinutes(info.totalCpuDuration().get().toMinutes());
            duration = duration.plusHours(info.totalCpuDuration().get().toHours());
            newList.get(info.command().toString()).setDuration(duration);
        } else {
            newList.put(info.command().toString(), new AutoTrackData(info.command().orElse("none"), LocalTime.of(info.totalCpuDuration().get().toHoursPart(), info.totalCpuDuration().get().toMinutesPart(), info.totalCpuDuration().get().toSecondsPart())));
        }

    }
}


