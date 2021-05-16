package gui.popups.action.treeItemAction;

import data.FileAccess;
import data.Recording;
import data.AutoTrackData;
import gui.controllers.MainController;
import gui.controllers.ProjectsTabController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.RootTreeItem;
import logic.treeItems.TaskTreeItem;

public class AddRecordingToProjectPopup extends TreeItemPopup {

    private ProjectTreeItem project;
    private TaskTreeItem task;
    private final AutoTrackData selectedItem;
    private final MainController mainController;
    private final TableView<AutoTrackData> autoTable;

    public AddRecordingToProjectPopup(AutoTrackData selectedItem, MainController mainController,
                                      TableView<AutoTrackData> autoTable) {
        super(ProjectsTabController.getActiveRoot(), "task");
        this.selectedItem = selectedItem;
        this.mainController = mainController;
        this.autoTable = autoTable;
    }

    /**
     * Pops up the popup for the user to interact with
     */
    @Override
    public void popup() {
        if (selectedItem != null) {
            RootTreeItem root = (RootTreeItem) treeItem;

            var window = addStage();
            window.setTitle("Add to project");

            ComboBox<ProjectTreeItem> projectComboBox = new ComboBox<>();
            ComboBox<TaskTreeItem> taskComboBox = new ComboBox<>();
            taskComboBox.setDisable(true);

            root.getJuniors().forEach((projectTreeItem) -> projectComboBox.getItems().add(projectTreeItem));


            var createButton = addButton("Add to project");
            var cancelButton = addButton("Cancel");

            var label = this.addLabel("Choose the project");

            mainButtonFunctionality(createButton, window);
            cancelButton.setCancelButton(true);
            cancelButton.setOnAction(event -> window.close());

            var display = addVBox(new Node[]{label, projectComboBox, taskComboBox, createButton, cancelButton});

            projectComboBox.setOnAction(event -> {
                project = projectComboBox.getValue();
                project.getJuniors().forEach(junior -> taskComboBox.getItems().add(junior));
                taskComboBox.setDisable(false);
            });

            taskComboBox.setOnAction(event -> {
                createButton.setDisable(false);
                task = taskComboBox.getValue();
            });
            setScene(window, display);
        }
    }

    /**
     * Method for adding default functionality and button set on action to the main button
     * @param button - the default button
     * @param stage - the Stage that everything is happening in
     */
    protected void mainButtonFunctionality(Button button, Stage stage) {
        super.mainButtonFunctionality(button, stage);
        button.setOnAction(event -> addRecord(stage));
    }

    /**
     * Method for adding a record to a certain project task's recordigns list
     * @param stage - the stage that is about to be closed
     */
    private void addRecord(Stage stage) {
        var recording = new Recording(task, selectedItem.calculateDurationInSec());
        task.getRecordings().add(recording);
        mainController.getMainTabService().addToHistory(mainController.getHistoryTabController(), recording);
        autoTable.getItems().remove(selectedItem);
        stage.close();
        FileAccess.saveData();
    }
}
