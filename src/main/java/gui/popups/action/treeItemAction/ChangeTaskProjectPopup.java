package gui.popups.action.treeItemAction;

import data.FileAccess;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import logic.treeItems.AbstractTreeItem;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.TaskTreeItem;

public class ChangeTaskProjectPopup extends TreeItemPopup {

    private ProjectTreeItem selectedProject;
    private final TaskTreeItem selectedTask;

    public ChangeTaskProjectPopup(AbstractTreeItem treeItem, String type) {
        super(treeItem, type);
        selectedTask = (TaskTreeItem) treeItem;
        selectedProject = (ProjectTreeItem) selectedTask.getParent();
    }

    /**
     * Pops up the stage of the ChangeTaskProjectPopup.
     */
    @Override
    public void popup() {

        var window = addStage();
        var label = this.addLabel("New project for '" + selectedTask.getValue() + "'.");
        var changeProjectButton = addButton("Change parent project");
        var cancelButton = addButton("Cancel");

        window.setTitle("Change project for task");

        ComboBox<ProjectTreeItem> projectComboBox = addProjectComboBox(selectedProject);
        mainButtonFunctionality(changeProjectButton, window);
        projectComboBox.valueProperty().addListener((observable, oldValue, newValue) -> comboboxListener(changeProjectButton, newValue));

        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(event -> window.close());

        var display = addVBox(new Node[]{projectComboBox, label, changeProjectButton, cancelButton});

        setScene(window, display);
    }

    /**
     * Action that changes the parent project of the chosen task
     * @param button - the default button
     * @param stage the Stage that everything is happening in
     */
    @Override
    protected void mainButtonFunctionality(Button button, Stage stage) {
        super.mainButtonFunctionality(button, stage);
        button.setOnAction(event -> {
            ((ProjectTreeItem) treeItem.getParent()).removeJunior(selectedTask);
            selectedProject.addJunior(selectedTask);
            sortItems(selectedTask);
            FileAccess.saveData();
            stage.close();
        });
    }

    /**
     * Listener for the combobox that checks if the chosen new parent is not the current parent
     * @param button - the default button
     * @param project - the current parent project of the selected task
     */
    private void comboboxListener(Button button, ProjectTreeItem project) {
        button.setDisable(project == treeItem.getParent());
        selectedProject = project;
    }
}
