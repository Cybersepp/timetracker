package logic.services;

import data.FileAccess;
import data.Recording;
import gui.controllers.GraphTabController;
import gui.controllers.ProjectsTabController;
import gui.popups.action.recordingAction.ChangeRecordingParentPopup;
import gui.popups.action.recordingAction.EditRecordingTimePopup;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.graph.GraphTimeCalculator;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.RootTreeItem;
import logic.treeItems.TaskTreeItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HistoryTabService  {

    private final TableView<Recording> table;
    private final TableColumn<Recording, String> projectColumn;
    private final TableColumn<Recording, String> taskColumn;
    private final TableColumn<Recording, String> startColumn;
    private final TableColumn<Recording, String> durationColumn;
    private GraphTabController graphTabController;

    private final ObservableList<Recording> records = FXCollections.observableArrayList();

    public HistoryTabService(TableView<Recording> table, TableColumn<Recording, String> projectColumn,
                             TableColumn<Recording, String> taskColumn, TableColumn<Recording, String> startColumn,
                             TableColumn<Recording, String> durationColumn) {
        this.table = table;
        this.projectColumn = projectColumn;
        this.taskColumn = taskColumn;
        this.startColumn = startColumn;
        this.durationColumn = durationColumn;
    }

    public ObservableList<Recording> getRecords() {
        return records;
    }

    private void initializeTableView() {
        RootTreeItem root = ProjectsTabController.getActiveRoot();
        List<ProjectTreeItem> projects = root.getJuniors();

        for (ProjectTreeItem project : projects) {
            List<TaskTreeItem> tasks = project.getJuniors();

            for (TaskTreeItem task : tasks) {
                List<Recording> recordings = task.getRecordings();
                this.records.addAll(recordings);
            }
        }
        configureColumns();
        table.setItems(records);
        var start = new ArrayList<TableColumn<Recording, String>>();
        start.add(startColumn);
        table.getSortOrder().setAll(start);
        table.setRowFactory(tableView -> tableRowContextMenu());
    }

    /**
     * Cell value factory method for populating history tab.
     */
    private void configureColumns() {
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("recordStart"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("durationInString"));
    }

    /**
     * When recording is added, history tab is updated and sorted so the user can see the newest recording.
     * @param recording is data object to be added.
     */
    public void addRecord(Recording recording) {
        records.add(recording);
        var start = new ArrayList<TableColumn<Recording, String>>();
        start.add(startColumn);
        table.getSortOrder().setAll(start);
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
        if (graphTabController != null) {
            showByTime(graphTabController.howManyDaysToShow(), graphTabController); //refreshes graph
        }
    }

    /**
     * Method for changing the task of the recording
     * @param recording - the selected Recording in the table
     */
    private void replaceRecording(Recording recording) {
        new ChangeRecordingParentPopup(recording).popup();
        table.refresh();
        if (graphTabController != null) {
            showByTime(graphTabController.howManyDaysToShow(), graphTabController); //refreshes graph
        }
    }

    /**
     * Method for deleting a recorded session
     * @param recording - the selected Recording in the table
     */
    private void deleteRecording(Recording recording) {
        var task = recording.getParentTask();
        task.getRecordings().remove(recording);
        table.getItems().remove(recording);
        if (graphTabController != null) {
            showByTime(graphTabController.howManyDaysToShow(), graphTabController); //refreshes graph
        }
        FileAccess.saveData();
    }

    public void initializeData() {
        initializeTableView();
    }


    /**
     * Method showByTime updates the graph for a certain time period in days.
     * Counting in descending order from the method call day.
     * Efficient method, since it sorts list by date and then starts from the initial date.
     * @param days is the given period to show.
     */
    public void showByTime(int days, GraphTabController graphTabController) {
        if (!getRecords().isEmpty()) {
            List<Recording> copyForComputing = new ArrayList<>(getRecords());
            var calculator = new GraphTimeCalculator(days);
            Map<String, BigDecimal> lastWeekProjectData = calculator.findRecordsByDays(copyForComputing);
            graphTabController.updateGraph(lastWeekProjectData);
            this.graphTabController = graphTabController;
        }
    }

}
