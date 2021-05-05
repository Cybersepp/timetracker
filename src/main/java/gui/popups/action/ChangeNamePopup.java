package gui.popups.action;

import data.FileAccess;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.treeItems.AbstractTreeItem;

public class ChangeNamePopup extends ActionPopup {

    public ChangeNamePopup(AbstractTreeItem treeItem) {
        super(treeItem, treeItem.toStringType());
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
        TextField textField = addTextField();
        textField.textProperty().addListener((observable, oldValue, newValue) -> textFieldListener(changeButton, textField, newValue));

        mainButtonFunctionality(changeButton, window, textField);
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
    protected void mainButtonFunctionality(Button button, Stage stage, TextField textField) {
        super.mainButtonFunctionality(button, stage, textField);
        button.setOnAction(e -> {
            treeItem.setValue(textField.getText().trim());
            stage.close();
            sortItems(treeItem);
            FileAccess.saveData();
        });
    }

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
