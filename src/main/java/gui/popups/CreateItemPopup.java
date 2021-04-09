package gui.popups;

import logic.treeItems.AbstractTreeItem;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.RootTreeItem;
import logic.treeItems.TaskTreeItem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CreateItemPopup extends AbstractPopup{

    private final AbstractTreeItem treeItem;
    private final String type;

    public CreateItemPopup(AbstractTreeItem treeItem, String type) {
        this.treeItem = treeItem;
        this.type = type;
    }

    public void popup() {
        Stage window = new Stage();
        defaultWindowSettings(window, 250, 300, 375, 450);

        Label label = new Label("Name your " + type);
        label.setFont(Font.font ("Verdana", FontWeight.BOLD, 16));
        TextField textField = new TextField();

        Button createButton = addButton("Create " + type);
        Button cancelButton = addButton("Cancel");
        createButtonFunctionality(treeItem, createButton, window, type, textField);
        cancelButton.setOnAction(event -> window.close());

        VBox display = new VBox(10);
        display.setPadding(new Insets(10, 40, 30, 40));
        display.setSpacing(10);
        display.getChildren().addAll(label, textField, createButton, cancelButton);
        display.setAlignment(Pos.CENTER);
        VBox.setMargin(textField,new Insets(15, 0, 30, 0));

        Scene scene1= new Scene(display, 300, 250);
        window.setScene(scene1);
        window.showAndWait();
    }

    private Button addButton(String name) {
        Button button = new Button(name);
        button.setFont(Font.font ("Verdana", 14));
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    private void createButtonFunctionality(AbstractTreeItem treeItem, Button button, Stage stage, String type, TextField textField) {

        stage.setTitle("Create a " + type);
        button.setStyle("-fx-background-color: #00B5FE");
        button.setOnAction(e -> {
            if (textField.getText().trim().isEmpty()) {
                new WarningPopup("You have not set a name for the " + type + "!").popup();
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
