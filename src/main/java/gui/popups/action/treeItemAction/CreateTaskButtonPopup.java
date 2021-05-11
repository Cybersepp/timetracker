package gui.popups.action.treeItemAction;

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

public class CreateTaskButtonPopup extends TreeItemPopup {

    private ProjectTreeItem project;

    public CreateTaskButtonPopup() {
        super(ProjectsTabController.getActiveRoot(), "task");
    }

    /**
     * Pops up the stage of the CreateTaskButtonPopup.
     */
    @Override
    public void popup() {
        var root = (RootTreeItem) treeItem;
        var window = addStage();
        var createButton = addButton("Create task");
        var cancelButton = addButton("Cancel");
        var label = this.addLabel("Name your task");
        var textField = addTextField();

        window.setTitle("Create a task");

        ComboBox<ProjectTreeItem> projectComboBox = new ComboBox<>();
        root.getJuniors().forEach(projectTreeItem -> projectComboBox.getItems().add(projectTreeItem));

        textField.textProperty().addListener((observable, oldValue, newValue) -> textFieldListener(projectComboBox, createButton, textField));
        projectComboBox.valueProperty().addListener((observable, oldValue, newValue) -> textFieldListener(projectComboBox, createButton, textField));

        mainButtonFunctionality(createButton, window, textField);
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(event -> window.close());

        var display = addVBox(new Node[]{projectComboBox, label, textField, createButton, cancelButton});
        VBox.setMargin(textField,new Insets(15, 0, 30, 0));

        setScene(window, display);
    }

    /**
     * Action that creates a new task with the chosen name
     * @param button the default button
     * @param stage the Stage that everything is happening in
     * @param textField input field
     */
    @Override
    protected void mainButtonFunctionality(Button button, Stage stage, TextField textField) {
        super.mainButtonFunctionality(button, stage, textField);
        button.setOnAction(e -> {
            var taskTreeItem = new TaskTreeItem(textField.getText().trim());
            project.addJunior(taskTreeItem);
            sortItems(taskTreeItem);
            FileAccess.saveData();
            stage.close();
        });
    }

    /**
     * Listener that makes sure that the user can not enter a task an existing or empty name and makes sure that the parent project is selected
     * @param projectComboBox - The combobox which holds all the existing projects
     * @param mainButton - the button that's state is going to be set
     * @param textField - the textField to be listened to
     */
    private void textFieldListener(ComboBox<ProjectTreeItem> projectComboBox, Button mainButton, TextField textField){
        if (projectComboBox.getValue() == null) {
            mainButton.setDisable(true);
            return;
        }
        final var sameName = projectComboBox.getValue().getChildren().stream()
                .filter(stringTreeItem -> stringTreeItem.getValue().equals(textField.getText().trim()))
                .map(TreeItem::getValue)
                .findFirst();

        if (textField.getText().isEmpty() || sameName.isPresent()) {
            mainButton.setDisable(true);
        }
        else {
            mainButton.setDisable(false);
            project = projectComboBox.getValue();
        }
    }
}
