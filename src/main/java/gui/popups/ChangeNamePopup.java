package gui.popups;

import data.FileAccess;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Treeitems.AbstractTreeItem;

public class ChangeNamePopup extends ActionPopup{

    public ChangeNamePopup(AbstractTreeItem treeItem) {
        super(treeItem, treeItem.toString());
    }

    @Override
    public void popup() {
        Stage window = addStage();

        Label label = this.addLabel("Rename " + type + " " + "'" + treeItem.getValue() + "'");
        TextField textField = addTextField();

        Button changeButton = addButton("Change name");
        Button cancelButton = addButton("Cancel");
        mainButtonFunctionality(treeItem, changeButton, window, textField);
        cancelButton.setOnAction(event -> window.close());
        changeButton.setDefaultButton(true);
        cancelButton.setCancelButton(true);

        VBox display = addVBox(new Node[]{label, textField, changeButton, cancelButton});
        VBox.setMargin(textField,new Insets(15, 0, 30, 0));

        setScene(window, display);
    }

    @Override
    protected void mainButtonFunctionality(AbstractTreeItem treeItem, Button button, Stage stage, TextField textField) {
        stage.setTitle("Change name");
        button.setStyle("-fx-background-color: #00B5FE");
        button.setOnAction(e -> {
            if (textField.getText().trim().isEmpty()) {
                new ErrorPopup("You can't set an empty name for your " + type + "!").popup();
                // TODO instead of throwing a warning popup, should have the OK button grayed out until something is entered (listener)
            }
            // TODO create warning popup if textField is a project / task with the given name already exists (else if)
            else {
                final var sameName = treeItem.getChildren().stream()
                        .filter(stringTreeItem -> stringTreeItem.getValue().equals(textField.getText().trim()))
                        .map(TreeItem::getValue)
                        .findFirst();
                if (sameName.isPresent()) {
                    new ErrorPopup("A " + type + " with the same name already exists!").popup();
                    return;
                }
                treeItem.setValue(textField.getText().trim());
                stage.close();
                FileAccess.saveData();
            }

        });
    }
}
