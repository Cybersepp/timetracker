package gui.controllers;

import data.tableview.AutoTrackData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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


    public void loadProcesses() throws Exception {
        switch (System.getProperty("os.name")) {
            case "Windows 10":
                loadProcessesWindows();
                break;
            case "linux":
                loadProcessesLinux();
                break;
            default:
                throw new IllegalStateException("Unexpected value!");
        }
    }


    public void loadProcessesLinux() throws Exception {
        newList = new HashMap<>();
        String username = System.getProperty("user.name");
        try {
            Process p = Runtime.getRuntime().exec("ps -u " + username);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = input.readLine();
            while ((line = input.readLine()) != null) {
                String[] data = line.trim().replaceAll(" +", " ").split(" ");
                if (newList.containsKey(data[3])) {
                    LocalTime duration = newList.get(data[3]).getDuration();
                    String[] timeStringSplitted = data[2].split(":");
                    duration = duration.plusSeconds(Long.parseLong(timeStringSplitted[2]));
                    duration = duration.plusMinutes(Long.parseLong(timeStringSplitted[1]));
                    duration = duration.plusHours(Long.parseLong(timeStringSplitted[0]));
                    newList.get(data[3]).setDuration(duration);
                    continue;
                }
                LocalTime time = LocalTime.parse(data[2], dtf);
                newList.put(data[3], new AutoTrackData(data[3], time));
            }

            if (baseList.isEmpty()) {
                baseList.putAll(newList);
            }
            input.close();
            configureColumns();
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

            ObservableList<AutoTrackData> helper = autoTable.getItems();
            List<AutoTrackData> conversionList = compareMap.values().stream().collect(Collectors.toList());
            helper.addAll(conversionList);
            autoTable.setItems(helper);
            baseList = newList;

        } catch (Exception err) {
            throw new Exception(err);
        }
    }


    public void loadProcessesWindows() {
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
