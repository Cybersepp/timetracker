package gui.popups.action;

import data.FileAccess;
import gui.controllers.ProjectsTabController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.treeItems.AbstractTreeItem;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.TaskTreeItem;

public class ChangeTaskProjectPopup extends ActionPopup{

    private ProjectTreeItem selectedProject;
    private TaskTreeItem selectedTask;

    public ChangeTaskProjectPopup(AbstractTreeItem treeItem, String type) {
        super(treeItem, type);
        selectedTask = (TaskTreeItem) treeItem;
        selectedProject = (ProjectTreeItem) selectedTask.getParent();
    }

    @Override
    public void popup() {

        Stage window = addStage();
        window.setTitle("Change project for task");

        ComboBox<ProjectTreeItem> projectComboBox = new ComboBox<>();
        ProjectsTabController.getProjects().getJuniors().forEach((projectTreeItem) -> projectComboBox.getItems().add(projectTreeItem));
        projectComboBox.getSelectionModel().select(selectedProject);
        projectComboBox.setMaxWidth(Double.MAX_VALUE);

        Label label = this.addLabel("New project for '" + selectedTask.getValue() + "'.");

        Button changeProjectButton = addButton("Change parent project");
        Button cancelButton = addButton("Cancel");
        mainButtonFunctionality(changeProjectButton, window);

        projectComboBox.valueProperty().addListener((observable, oldValue, newValue) -> comboboxListener(changeProjectButton, newValue));

        changeProjectButton.setDefaultButton(true);
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(event -> window.close());

        VBox display = addVBox(new Node[]{projectComboBox, label, changeProjectButton, cancelButton});

        setScene(window, display);
    }

    @Override
    protected void mainButtonFunctionality(Button button, Stage stage) {
        super.mainButtonFunctionality(button, stage);
        button.setOnAction(e -> {
            ((ProjectTreeItem) treeItem.getParent()).removeJunior(selectedTask);
            selectedProject.addJunior(selectedTask);
            FileAccess.saveData();
            stage.close();
        });
    }

    private void comboboxListener(Button button, ProjectTreeItem project) {
        button.setDisable(project == treeItem.getParent());
        selectedProject = project;
    }
}
