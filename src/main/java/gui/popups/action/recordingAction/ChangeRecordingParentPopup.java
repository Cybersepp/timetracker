package gui.popups.action.recordingAction;

import data.FileAccess;
import data.Recording;
import gui.controllers.ProjectsTabController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.TaskTreeItem;

public class ChangeRecordingParentPopup extends RecordingPopup {

    public ChangeRecordingParentPopup(Recording recording) {
        super(recording);
        selectedTask = recording.getParentTask();
    }

    private TaskTreeItem selectedTask;

    /**
     * Pops up the popup for the user to interact with
     */
    @Override
    public void popup() {
        var window = addStage();
        var mainButton = addButton("Change task");
        var cancelButton = addButton("Cancel");

        var projectComboBox = addProjectComboBox(recording.getParentProject());
        var projectLabel = addLabel("project: ");
        var projectHBox = addHBox(new Node[]{projectLabel, projectComboBox});

        var taskComboBox = addTaskComboBox();
        var taskLabel = addLabel("task: ");
        var taskHBox = addHBox(new Node[]{taskLabel, taskComboBox});

        projectComboBox.valueProperty().addListener((observable, oldValue, newValue) -> projectComboboxListener(taskComboBox, newValue, mainButton));
        taskComboBox.valueProperty().addListener((observable, oldValue, newValue) -> taskComboboxListener(newValue, mainButton));

        window.setTitle("Change recording's task");

        mainButtonFunctionality(mainButton, window);
        cancelButton.setOnAction(event -> window.close());
        cancelButton.setCancelButton(true);

        var display = addVBox(new Node[]{projectHBox, taskHBox, mainButton, cancelButton});

        setScene(window, display);
    }

    /**
     * Method for creating a Combobox with all the created projects inside (active and archived)
     * @param selectedProject - the project that is going to be selected at start
     * @return ComboBox with all the projects
     */
    @Override
    protected ComboBox<ProjectTreeItem> addProjectComboBox(ProjectTreeItem selectedProject) {
        var projectComboBox = super.addProjectComboBox(selectedProject);
        ProjectsTabController.getArchivedRoot().getJuniors().forEach(projectTreeItem -> projectComboBox.getItems().add(projectTreeItem));
        return projectComboBox;
    }

    /**
     * Method for creating a ComboBox with all the tasks under the parent Project of the recording
     * @return ComboBox for tasks of the selected project
     */
    private ComboBox<TaskTreeItem> addTaskComboBox() {
        ComboBox<TaskTreeItem> taskComboBox = new ComboBox<>();
        recording.getParentProject().getJuniors().forEach(task -> taskComboBox.getItems().add(task));
        taskComboBox.getSelectionModel().select(recording.getParentTask());
        taskComboBox.setMaxWidth(Double.MAX_VALUE);
        return taskComboBox;
    }

    /**
     * Listener for the Combobox of the projects that changes the task combo box according to the selected project
     * @param taskComboBox - ComboBox for the selected project's tasks
     * @param newProject - new project that has been selected in the comboBox
     * @param mainButton - the button which can be used to change the parent of the recording
     */
    private void projectComboboxListener(ComboBox<TaskTreeItem> taskComboBox, ProjectTreeItem newProject, Button mainButton) {
        taskComboBox.getItems().removeAll(taskComboBox.getItems());
        newProject.getJuniors().forEach(task -> taskComboBox.getItems().add(task));
        mainButton.setDisable(true);
    }

    /**
     * Listener for the TaskComboBox which changes the selectedTask variable
     * @param newValue - new task that has been selected in the comboBox for the tasks
     * @param mainButton - the button which can be used to change the parent of the recording
     */
    private void taskComboboxListener(TaskTreeItem newValue, Button mainButton) {
        if (newValue == null || newValue == recording.getParentTask()) {
            return;
        }
        selectedTask = newValue;
        mainButton.setDisable(false);
    }

    /**
     * Method for adding configurations and Action to the main button
     * @param button - the default button
     * @param stage - the Stage that everything is happening in
     */
    @Override
    protected void mainButtonFunctionality(Button button, Stage stage) {
        super.mainButtonFunctionality(button, stage);
        button.setOnAction(event -> mainButtonListener(stage));
    }

    /**
     * Method for setting a new parent for the recording
     * @param stage - the stage to be closed
     */
    private void mainButtonListener(Stage stage) {
        recording.setParentTask(selectedTask);
        FileAccess.saveData();
        stage.close();
    }
}
