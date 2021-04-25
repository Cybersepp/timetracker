package gui.controllers;

import data.RecordEntryData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.treeItems.*;
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

    /**
     * Upon initialization it reads required data for populating history overview from each project,
     * task and record object and creates a new data object.
     */
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


    /**
     * In order for historyTabController to know its parent controller, main controller passes itself
     * to its subcontrollers.
     *
     * @param main
     */
    public void init(MainController main) {
        mainController = main;
    }

    /**
     * Cell value factory method for populating history tab.
     */
    public void configureColumns() {
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("durationInSec"));
    }

    /**
     * Method showByTime updates the graph for a certain time period in days.
     * Counting in descending order from the method call day.
     * Efficient method, since it sorts list by date and then starts from the initial date.
     *
     * @param days is the given period to show.
     * @throws ParseException is thrown if can't parse the date.
     */
    public void showByTime(int days) throws ParseException {
        if (records.size() != 0) {
            List<RecordEntryData> copyForComputing = new ArrayList<>(records);
            GraphTimeCalculator calculator = new GraphTimeCalculator(days);
            Map<String, Integer> lastWeekProjectData = calculator.findRecordsByDays(copyForComputing);
            GraphTabController graphTabController = mainController.getGraphTabController();
            graphTabController.updateGraph(lastWeekProjectData);
        }
    }

    /**
     * When record is added, history tab is updated and sorted so the user can see the newest record.
     *
     * @param record is data object to be added.
     */
    public void addRecord(RecordEntryData record) {
        records.add(record);
        table.getSortOrder().setAll(startColumn);
    }

    public int getRecordLenght() {
        return records.size();
    }
}
