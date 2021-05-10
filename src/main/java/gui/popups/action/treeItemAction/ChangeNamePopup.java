package gui.popups.action.treeItemAction;

import data.FileAccess;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.treeItems.AbstractTreeItem;

public class ChangeNamePopup extends TreeItemPopup {

    public ChangeNamePopup(AbstractTreeItem treeItem) {
        super(treeItem, treeItem.toStringType());
    }

    /**
     * Pops up the stage of the ChangeNamePopup.
     */
    @Override
    public void popup() {
        var window = addStage();
        var changeButton = addButton("Change name");
        var cancelButton = addButton("Cancel");
        var label = this.addLabel("Rename " + type + " " + "'" + treeItem.getValue() + "'");
        var textField = addTextField();

        window.setTitle("Change name");

        textField.textProperty().addListener((observable, oldValue, newValue) -> textFieldListener(changeButton, textField, newValue));

        mainButtonFunctionality(changeButton, window, textField);
        cancelButton.setOnAction(event -> window.close());
        cancelButton.setCancelButton(true);

        var display = addVBox(new Node[]{label, textField, changeButton, cancelButton});
        VBox.setMargin(textField,new Insets(15, 0, 30, 0));

        setScene(window, display);
    }

    /**
     * Changes the name of the chosen item.
     * @param button the default button
     * @param stage the Stage that everything is happening in
     * @param textField input field for the new name
     */
    @Override
    protected void mainButtonFunctionality(Button button, Stage stage, TextField textField) {
        super.mainButtonFunctionality(button, stage, textField);
        button.setOnAction(e -> {
            treeItem.setValue(textField.getText().trim());
            stage.close();
            sortItems((AbstractTreeItem) treeItem.getParent());
            FileAccess.saveData();
        });
    }

    /**
     * Listener that makes sure that the user can not enter a task or project with an existing or empty name
     * @param mainButton - the button that's state is going to be set
     * @param textField - the textField to be listened to
     * @param newValue - the new value of the textField item
     */
    private void textFieldListener(Button mainButton, TextField textField, String newValue){
        final var sameName = treeItem.getParent().getChildren().stream()
                .filter(stringTreeItem -> stringTreeItem.getValue().equals(textField.getText().trim()))
                .map(TreeItem::getValue)
                .findFirst();

        if (newValue.isEmpty()) {
            mainButton.setDisable(true);
        }
        else mainButton.setDisable(sameName.isPresent());
    }
}
