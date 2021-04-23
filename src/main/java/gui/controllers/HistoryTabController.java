package gui.controllers;

import data.RecordEntryData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Treeitems.ProjectTreeItem;
import logic.Treeitems.RootTreeItem;
import logic.Treeitems.TaskTreeItem;
import logic.graph.GraphTimeCalculator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HistoryTabController {

    @FXML
    private TableView<RecordEntryData> table;

    @FXML
    private TableColumn<RecordEntryData, String> projectColumn;

    @FXML
    private TableColumn<RecordEntryData, String> taskColumn;

    @FXML
    private TableColumn<RecordEntryData, String> startColumn;

    @FXML
    private TableColumn<RecordEntryData, Integer> durationColumn;

    @FXML
    private Label historyLabel;

    private ProjectsTabController projectsTabController;

    private MainController mainController;

    ObservableList<RecordEntryData> records = FXCollections.observableArrayList();

    public void initialize() {
        RootTreeItem root = ProjectsTabController.getProjects();
        List<ProjectTreeItem> projects = root.getJuniors();

        for (ProjectTreeItem project : projects) {
            List<TaskTreeItem> tasks = project.getJuniors();

            for (TaskTreeItem task : tasks) {
                String projectName = task.getParent().getValue();
                String taskName = task.getValue();
                List<String> entries = task.getRecords();

                for (String record : entries) {
                    String[] splittedRecord = record.split(", ");
                    String start = splittedRecord[0];
                    String duration = splittedRecord[2];
                    records.add(new RecordEntryData(projectName, taskName, start, duration));
                }
            }
        }
        configureColumns();
        table.setItems(records);
        table.getSortOrder().setAll(startColumn);
    }

    public void init(MainController main) {
        mainController = main;
    }

    public void configureColumns() {
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("durationInSec"));
    }

    public void showByTime(int days) throws ParseException {
        List<RecordEntryData> copyForComputing = new ArrayList<>(records);
        GraphTimeCalculator calculator = new GraphTimeCalculator(days);
        Map<String, Integer> lastWeekProjectData = calculator.findRecordsByDays(copyForComputing);
        GraphTabController graphTabController = mainController.getGraphTabController();
        graphTabController.updateGraph(lastWeekProjectData);

    }

    public void addRecord(RecordEntryData record) {
        records.add(record);
        table.getSortOrder().setAll(startColumn);
    }
}
