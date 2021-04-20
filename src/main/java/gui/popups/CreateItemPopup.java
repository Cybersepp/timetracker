package gui.popups;

import data.FileAccess;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import logic.Treeitems.AbstractTreeItem;
import logic.Treeitems.ProjectTreeItem;
import logic.Treeitems.RootTreeItem;
import logic.Treeitems.TaskTreeItem;
import javafx.geometry.Insets;
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
        Stage window = addStage();

        Label label = this.addLabel("Name your " + type);
        TextField textField = addTextField();

        Button createButton = addButton("Create " + type);
        Button cancelButton = addButton("Cancel");
        mainButtonFunctionality(treeItem, createButton, window, textField);
        createButton.setDefaultButton(true);
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(event -> window.close());

        VBox display = addVBox(new Node[]{label, textField, createButton, cancelButton});
        VBox.setMargin(textField,new Insets(15, 0, 30, 0));

        setScene(window, display);
    }

    @Override
    protected void mainButtonFunctionality(AbstractTreeItem treeItem, Button button, Stage stage, TextField textField) {
        stage.setTitle("Create a " + type);
        button.setStyle("-fx-background-color: #00B5FE");
        button.setOnAction(e -> {
            if (textField.getText().trim().isEmpty()) {
                new ErrorPopup("You have not set a name for the " + type + "!").popup();
                // TODO instead of throwing a warning popup, should have the OK button grayed out until something is entered (listener)
            }
            else {
                final var sameName = treeItem.getChildren().stream()
                        .filter(stringTreeItem -> stringTreeItem.getValue().equals(textField.getText().trim()))
                        .map(TreeItem::getValue)
                        .findFirst();
                if (sameName.isPresent()) {
                    new ErrorPopup("A " + type + " with the same name already exists!").popup();
                    return;
                }

                if (treeItem.getClass().equals(RootTreeItem.class)) {
                    createProjectBranch((RootTreeItem) treeItem, textField);
                }
                else if (treeItem.getClass().equals(ProjectTreeItem.class)) {
                    createTaskLeaf((ProjectTreeItem) treeItem, textField);
                }

                FileAccess.saveData();
                stage.close();
            }

        });
    }

    private void createProjectBranch(RootTreeItem root, TextField textField){

        ProjectTreeItem newProject = new ProjectTreeItem(textField.getText().trim());
        root.addJunior(newProject);
    }

    private void createTaskLeaf(ProjectTreeItem project, TextField textField) {

        TaskTreeItem newTask = new TaskTreeItem(textField.getText().trim());
        project.addJunior(newTask);
    }
}
