package gui.popups.action.treeItemAction;

import data.FileAccess;
import data.Recording;
import data.AutoTrackData;
import gui.controllers.MainController;
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
    private final MainController mainController;

    public AddToProjectPopup(AutoTrackData selectedItem, MainController mainController) {
        super(ProjectsTabController.getActiveRoot(), "task");
        this.selectedItem = selectedItem;
        this.mainController = mainController;
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
        button.setOnAction(event -> addRecord(stage));
    }

    private void addRecord(Stage stage) {
        var recording = new Recording(task, selectedItem.getStartTime(),selectedItem.getEndTime());
        recording.setRecordEnd();
        task.getRecordings().add(recording);
        mainController.getMainTabService().addToHistory(mainController.getHistoryTabController(), recording);
        stage.close();
        FileAccess.saveData();
    }
}
