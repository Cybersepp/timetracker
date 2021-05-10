package gui.popups.action.treeItemAction;

import data.Recording;
import data.tableview.AutoTrackData;
import gui.controllers.ProjectsTabController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.RootTreeItem;
import logic.treeItems.TaskTreeItem;

public class AddToProjectPopup extends TreeItemPopup {

    private ProjectTreeItem project;
    private TaskTreeItem task;
    private final AutoTrackData selectedItem;

    public AddToProjectPopup(AutoTrackData selectedItem) {
        super(ProjectsTabController.getProjects(), "task");
        this.selectedItem = selectedItem;
    }

    @Override
    public void popup() {
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

    protected void mainButtonFunctionality(Button button, Stage stage) {
        super.mainButtonFunctionality(button, stage);
        button.setOnAction(event -> {
            stage.close();
            addRecord();
        });
    }

    public TaskTreeItem addRecord() {
        var recording = new Recording(task, selectedItem.calculateDuration());
        recording.setRecordEnd(selectedItem.getInitialDate());
        task.getRecordings().add(recording);
        return task;
    }
}
