package gui.popups.action;

import data.FileAccess;
import gui.popups.action.ActionPopup;
import javafx.scene.Node;
import logic.treeItems.AbstractTreeItem;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.RootTreeItem;
import logic.treeItems.TaskTreeItem;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateItemPopup extends ActionPopup {

    public CreateItemPopup(AbstractTreeItem treeItem, String type) {
        super(treeItem, type);
    }

    /**
     * Pops up the stage of the CreateItemPopup.
     */
    @Override
    public void popup() {
        Stage window = addStage();
        window.setTitle("Create a " + type);

        Button createButton = addButton("Create " + type);
        Button cancelButton = addButton("Cancel");

        Label label = this.addLabel("Name your " + type);
        TextField textField = addTextField(createButton);

        mainButtonFunctionality(treeItem, createButton, window, textField);
        createButton.setDefaultButton(true);
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(event -> window.close());

        VBox display = addVBox(new Node[]{label, textField, createButton, cancelButton});
        VBox.setMargin(textField,new Insets(15, 0, 30, 0));

        setScene(window, display);
    }

    /**
     * Creates a new child for the chosen item.
     * @param textField input field for the name of the child
     */
    @Override
    protected void mainButtonFunctionality(AbstractTreeItem treeItem, Button button, Stage stage, TextField textField) {
        button.setStyle("-fx-background-color: #00B5FE");
        button.setOnAction(e -> {
            if (treeItem.getClass().equals(RootTreeItem.class)) {
                createProjectBranch((RootTreeItem) treeItem, textField);
            }
            else if (treeItem.getClass().equals(ProjectTreeItem.class)) {
                createTaskLeaf((ProjectTreeItem) treeItem, textField);
            }
            FileAccess.saveData();
            stage.close();
        });
    }

    /**
     * Creates a new project.
     * @param root the parent root of the project
     * @param textField the text field where the name for the new project is entered
     */
    private void createProjectBranch(RootTreeItem root, TextField textField){
        ProjectTreeItem newProject = new ProjectTreeItem(textField.getText().trim());
        root.addJunior(newProject);
    }

    /**
     * Creates a new task.
     * @param project the parent project of the task
     * @param textField the text field where the name for the new task is entered
     */
    private void createTaskLeaf(ProjectTreeItem project, TextField textField) {
        TaskTreeItem newTask = new TaskTreeItem(textField.getText().trim());
        project.addJunior(newTask);
    }
}
