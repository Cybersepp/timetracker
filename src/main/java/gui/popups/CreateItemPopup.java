package gui.popups;

import logic.Treeitems.AbstractTreeItem;
import logic.Treeitems.ProjectTreeItem;
import logic.Treeitems.RootTreeItem;
import logic.Treeitems.TaskTreeItem;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateItemPopup extends ActionPopup{

    public CreateItemPopup(AbstractTreeItem treeItem, String type) {
        super(treeItem, type);
    }

    @Override
    public void popup() {
        Stage window = new Stage();
        defaultWindowSettings(window, 250, 300, 375, 450);

        Label label = this.addLabel("Name your " + type);
        TextField textField = new TextField();

        Button createButton = addButton("Create " + type);
        Button cancelButton = addButton("Cancel");
        mainButtonFunctionality(treeItem, createButton, window, textField);
        cancelButton.setOnAction(event -> window.close());

        VBox display = this.addVBox();
        display.getChildren().addAll(label, textField, createButton, cancelButton);
        VBox.setMargin(textField,new Insets(15, 0, 30, 0));

        Scene scene1= new Scene(display, 300, 250);
        window.setScene(scene1);
        window.showAndWait();
    }

    @Override
    protected void mainButtonFunctionality(AbstractTreeItem treeItem, Button button, Stage stage, TextField textField) {
        stage.setTitle("Create a " + type);
        button.setStyle("-fx-background-color: #00B5FE");
        button.setOnAction(e -> {
            if (textField.getText().trim().isEmpty()) {
                new WarningPopup("You have not set a name for the " + type + "!").popup();
                // TODO instead of throwing a warning popup, should have the OK button grayed out until something is entered (listener)
            }
            // TODO create warning popup if textField is a project / task with the given name already exists (else if)
            else {

                if (treeItem.getClass().equals(RootTreeItem.class)) {
                    createProjectBranch((RootTreeItem) treeItem, textField);
                }
                if (treeItem.getClass().equals(ProjectTreeItem.class)) {
                    createTaskLeaf((ProjectTreeItem) treeItem, textField);
                }
                stage.close();
            }

        });
    }

    private void createProjectBranch(RootTreeItem root, TextField textField){

        ProjectTreeItem newProject = new ProjectTreeItem(textField.getText());
        root.addJunior(newProject);
    }

    private void createTaskLeaf(ProjectTreeItem project, TextField textField) {

        TaskTreeItem newTask = new TaskTreeItem(textField.getText());
        project.addJunior(newTask);
    }
}
