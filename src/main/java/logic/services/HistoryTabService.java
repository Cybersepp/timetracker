package logic.services;

import data.FileAccess;
import data.Recording;
import gui.controllers.ProjectsTabController;
import gui.popups.action.recordingAction.ChangeRecordingParentPopup;
import gui.popups.action.recordingAction.EditRecordingTimePopup;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.RootTreeItem;
import logic.treeItems.TaskTreeItem;
import java.util.List;

public class HistoryTabService implements Service {

    private final TableView<Recording> table;
    private final TableColumn<Recording, String> projectColumn;
    private final TableColumn<Recording, String> taskColumn;
    private final TableColumn<Recording, String> startColumn;
    private final TableColumn<Recording, Integer> durationColumn;

    private final ObservableList<Recording> records = FXCollections.observableArrayList();

    public HistoryTabService(TableView<Recording> table, TableColumn<Recording, String> projectColumn,
                             TableColumn<Recording, String> taskColumn, TableColumn<Recording, String> startColumn,
                             TableColumn<Recording, Integer> durationColumn) {
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
     * Cell value factory method for populating history tab.
     */
    private void configureColumns() {
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("recordStart"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("durationInSec"));
    }

    /**
     * When record is added, history tab is updated and sorted so the user can see the newest record.
     * @param record is data object to be added.
     */
    public void addRecord(Recording record) {
        records.add(record);
        table.getSortOrder().setAll(startColumn);
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

    public void initializeData() {
        initializeTableView();
    }
}
