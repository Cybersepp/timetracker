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

        window.setTitle("Change task");

        mainButtonFunctionality(mainButton, window);
        cancelButton.setOnAction(event -> window.close());
        cancelButton.setCancelButton(true);

        var display = addVBox(new Node[]{projectHBox, taskHBox, mainButton, cancelButton});

        setScene(window, display);
    }

    @Override
    protected ComboBox<ProjectTreeItem> addProjectComboBox(ProjectTreeItem selectedProject) {
        var projectComboBox = super.addProjectComboBox(selectedProject);
        ProjectsTabController.getArchived().getJuniors().forEach(projectTreeItem -> projectComboBox.getItems().add(projectTreeItem));
        return projectComboBox;
    }

    private ComboBox<TaskTreeItem> addTaskComboBox() {
        ComboBox<TaskTreeItem> taskComboBox = new ComboBox<>();
        recording.getParentProject().getJuniors().forEach(task -> taskComboBox.getItems().add(task));
        taskComboBox.getSelectionModel().select(recording.getParentTask());
        taskComboBox.setMaxWidth(Double.MAX_VALUE);
        return taskComboBox;
    }

    private void projectComboboxListener(ComboBox<TaskTreeItem> taskComboBox, ProjectTreeItem newProject, Button mainButton) {
        taskComboBox.getItems().removeAll(taskComboBox.getItems());
        newProject.getJuniors().forEach(task -> taskComboBox.getItems().add(task));
        mainButton.setDisable(true);
    }

    private void taskComboboxListener(TaskTreeItem newValue, Button mainButton) {
        if (newValue == null || newValue == recording.getParentTask()) {
            return;
        }
        selectedTask = newValue;
        mainButton.setDisable(false);
    }

    @Override
    protected void mainButtonFunctionality(Button button, Stage stage) {
        super.mainButtonFunctionality(button, stage);
        button.setOnAction(event -> mainButtonListener(stage));
    }

    private void mainButtonListener(Stage stage) {
        FileAccess.saveData();
        stage.close();
    }
}
