package gui.popups.action;

import data.FileAccess;
import gui.popups.action.ActionPopup;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.treeItems.AbstractTreeItem;

public class ChangeNamePopup extends ActionPopup {

    public ChangeNamePopup(AbstractTreeItem treeItem) {
        super(treeItem, treeItem.toString());
    }

    /**
     * Pops up the stage of the ChangeNamePopup.
     */
    @Override
    public void popup() {
        Stage window = addStage();
        window.setTitle("Change name");

        Button changeButton = addButton("Change name");
        Button cancelButton = addButton("Cancel");

        Label label = this.addLabel("Rename " + type + " " + "'" + treeItem.getValue() + "'");
        TextField textField = addTextField(changeButton);

        mainButtonFunctionality(treeItem, changeButton, window, textField);
        cancelButton.setOnAction(event -> window.close());
        changeButton.setDefaultButton(true);
        cancelButton.setCancelButton(true);

        VBox display = addVBox(new Node[]{label, textField, changeButton, cancelButton});
        VBox.setMargin(textField,new Insets(15, 0, 30, 0));

        setScene(window, display);
    }

    /**
     * Changes the name of the chosen item.
     * @param textField input field for the new name
     */
    @Override
    protected void mainButtonFunctionality(AbstractTreeItem treeItem, Button button, Stage stage, TextField textField) {
        button.setStyle("-fx-background-color: #00B5FE");
        button.setOnAction(e -> {
            treeItem.setValue(textField.getText().trim());
            stage.close();
            FileAccess.saveData();
        });
    }
}
