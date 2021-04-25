package gui.popups.action;

import data.FileAccess;
import gui.controllers.ProjectsTabController;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.RootTreeItem;
import logic.treeItems.TaskTreeItem;

public class CreateTaskButtonPopup extends ActionPopup{

    private ProjectTreeItem project;

    public CreateTaskButtonPopup() {
        super(ProjectsTabController.getProjects(), "task");
    }

    @Override
    public void popup() {
        RootTreeItem root = (RootTreeItem) treeItem;

        Stage window = addStage();
        window.setTitle("Create a task");

        ComboBox<ProjectTreeItem> projectComboBox = new ComboBox<>();
        root.getJuniors().forEach((projectTreeItem) -> projectComboBox.getItems().add(projectTreeItem));

        Button createButton = addButton("Create task");
        Button cancelButton = addButton("Cancel");

        Label label = this.addLabel("Name your task");
        TextField textField = addTextField();
        textField.textProperty().addListener((observable, oldValue, newValue) -> textFieldListener(projectComboBox, createButton, textField, newValue));
        projectComboBox.valueProperty().addListener((observable, oldValue, newValue) -> textFieldListener(projectComboBox, createButton, textField, newValue.getValue()));

        mainButtonFunctionality(createButton, window, textField);
        createButton.setDefaultButton(true);
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(event -> window.close());

        VBox display = addVBox(new Node[]{projectComboBox, label, textField, createButton, cancelButton});
        VBox.setMargin(textField,new Insets(15, 0, 30, 0));

        setScene(window, display);
    }

    @Override
    protected void mainButtonFunctionality(Button button, Stage stage, TextField textField) {
        super.mainButtonFunctionality(button, stage, textField);
        button.setOnAction(e -> {
            project.addJunior(new TaskTreeItem(textField.getText().trim()));
            FileAccess.saveData();
            stage.close();
        });
    }

    private void textFieldListener(ComboBox<ProjectTreeItem> projectComboBox, Button mainButton, TextField textField, String newValue){
        final var sameName = projectComboBox.getValue().getChildren().stream()
                .filter(stringTreeItem -> stringTreeItem.getValue().equals(textField.getText().trim()))
                .map(TreeItem::getValue)
                .findFirst();

        if (newValue.isEmpty() || sameName.isPresent()) {
            mainButton.setDisable(true);
        }
        else {
            mainButton.setDisable(false);
            project = projectComboBox.getValue(); //TODO is this a good way to do this?
        }
    }
}