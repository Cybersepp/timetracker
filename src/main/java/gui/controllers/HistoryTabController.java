package gui.controllers;

import data.FileAccess;
import data.Recording;
import gui.popups.action.recordingAction.ChangeRecordingParentPopup;
import gui.popups.action.recordingAction.EditRecordingTimePopup;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.graph.GraphTimeCalculator;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.RootTreeItem;
import logic.treeItems.TaskTreeItem;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HistoryTabController {

    @FXML
    private TableView<Recording> table;

    @FXML
    private TableColumn<Recording, String> projectColumn;

    @FXML
    private TableColumn<Recording, String> taskColumn;

    @FXML
    private TableColumn<Recording, String> startColumn;

    @FXML
    private TableColumn<Recording, Integer> durationColumn;

    @FXML
    private Label historyLabel;

    private ProjectsTabController projectsTabController;

    private MainController mainController;

    ObservableList<Recording> records = FXCollections.observableArrayList();

    /**
     * Upon initialization it reads required data for populating history overview from each project,
     * task and record object and creates a new data object.
     */
    public void initialize() {
        RootTreeItem root = ProjectsTabController.getProjects();
        List<ProjectTreeItem> projects = root.getJuniors();

        // FIXME instead of iterating through it here can we for example iterate it with reading from file
        for (ProjectTreeItem project : projects) {
            List<TaskTreeItem> tasks = project.getJuniors();

            for (TaskTreeItem task : tasks) {
                List<Recording> recordings = task.getRecordings();
                this.records.addAll(recordings);
            }
        }
        configureColumns();
        table.setItems(records);
        table.getSortOrder().setAll(startColumn);
        table.setRowFactory(tableView -> tableRowContextMenu());
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
        startColumn.setCellValueFactory(new PropertyValueFactory<>("recordStart"));
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

    public void showByTime(int days) {
        if (!records.isEmpty()) {
            List<Recording> copyForComputing = new ArrayList<>(records);
            var calculator = new GraphTimeCalculator(days);
            Map<String, Integer> lastWeekProjectData = calculator.findRecordsByDays(copyForComputing);
            var graphTabController = mainController.getGraphTabController();
            graphTabController.updateGraph(lastWeekProjectData);
        }
    }

    /**
     * When record is added, history tab is updated and sorted so the user can see the newest record.
     *
     * @param record is data object to be added.
     */
    public void addRecord(Recording record) {
        records.add(record);
        table.getSortOrder().setAll(startColumn);
    }

    public int getRecordLength() {
        return records.size();
    }

    private TableRow<Recording> tableRowContextMenu () {
        final TableRow<Recording> row = new TableRow<>();
        final var rowMenu = new ContextMenu();

        var editItem = new MenuItem("Edit");
        var replaceItem = new MenuItem("Change task");
        var removeItem = new MenuItem("Delete");
        editItem.setOnAction(event -> editRecording(row.getItem()));
        replaceItem.setOnAction(event -> replaceRecording(row.getItem()));
        removeItem.setOnAction(event -> deleteRecording(row.getItem()));
        rowMenu.getItems().addAll(editItem, replaceItem, removeItem);

        // display ContextMenu only for filled rows
        row.contextMenuProperty().bind(
                Bindings.when(row.emptyProperty())
                        .then((ContextMenu) null)
                        .otherwise(rowMenu));
        return row;
    }

    /**
     * Method for editing the time period of the recording
     * @param recording - the selected Recording in the table
     */
    private void editRecording(Recording recording) {
        new EditRecordingTimePopup(recording).popup();
        table.refresh();
    }

    /**
     * Method for changing the task of the recording
     * @param recording - the selected Recording in the table
     */
    private void replaceRecording(Recording recording) {
        new ChangeRecordingParentPopup(recording).popup();
        table.refresh();
    }

    /**
     * Method for deleting a recorded session
     * @param recording - the selected Recording in the table
     */
    private void deleteRecording(Recording recording) {
        var task = recording.getParentTask();
        task.getRecordings().remove(recording);
        table.getItems().remove(recording);
        FileAccess.saveData();
    }
}
