package gui.popups.action;

import data.FileAccess;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.treeItems.AbstractTreeItem;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.RootTreeItem;
import logic.treeItems.TaskTreeItem;

public class CreateItemPopup extends ActionPopup {

    public CreateItemPopup(AbstractTreeItem treeItem, String type) {
        super(treeItem, type);
    }

    /**
     * Pops up the stage of the CreateItemPopup.
     */
    @Override
    public void popup() {
        var window = addStage();
        var createButton = addButton("Create " + type);
        var cancelButton = addButton("Cancel");
        var label = this.addLabel("Name your " + type);
        var textField = addTextField();

        window.setTitle("Create a " + type);

        textField.textProperty().addListener((observable, oldValue, newValue) -> textFieldListener(createButton, textField, newValue));

        mainButtonFunctionality(createButton, window, textField);
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(event -> window.close());

        var display = addVBox(new Node[]{label, textField, createButton, cancelButton});
        VBox.setMargin(textField,new Insets(15, 0, 30, 0));

        setScene(window, display);
    }

    /**
     * Creates a new child for the chosen item.
     * @param button the default button
     * @param stage the Stage that everything is happening in
     * @param textField input field for the name of the child
     */
    @Override
    protected void mainButtonFunctionality(Button button, Stage stage, TextField textField) {
        super.mainButtonFunctionality(button, stage, textField);
        button.setOnAction(e -> {
            if (treeItem.getClass().equals(RootTreeItem.class)) {
                createProjectBranch((RootTreeItem) treeItem, textField);
            }
            else if (treeItem.getClass().equals(ProjectTreeItem.class)) {
                createTaskLeaf((ProjectTreeItem) treeItem, textField);
            }
            sortItems(treeItem);
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
        var newProject = new ProjectTreeItem(textField.getText().trim());
        root.addJunior(newProject);
    }

    /**
     * Creates a new task.
     * @param project the parent project of the task
     * @param textField the text field where the name for the new task is entered
     */
    private void createTaskLeaf(ProjectTreeItem project, TextField textField) {
        var newTask = new TaskTreeItem(textField.getText().trim());
        project.addJunior(newTask);
    }

    /**
     * Listener that makes sure that the user can not enter a task or project with an existing or empty name
     * @param mainButton - the button that's state is going to be set
     * @param textField - the textField to be listened to
     * @param newValue - the new value of the textField item
     */
    private void textFieldListener(Button mainButton, TextField textField, String newValue){
        final var sameName = treeItem.getChildren().stream()
                .filter(stringTreeItem -> stringTreeItem.getValue().equals(textField.getText().trim()))
                .map(TreeItem::getValue)
                .findFirst();

        if (newValue.isEmpty()) {
            mainButton.setDisable(true);
        }
        else mainButton.setDisable(sameName.isPresent());
    }
}
