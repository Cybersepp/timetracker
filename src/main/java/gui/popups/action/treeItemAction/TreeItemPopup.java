package gui.popups.action.treeItemAction;

import gui.popups.action.ActionPopup;
import javafx.scene.control.TextField;
import logic.treeItems.AbstractTreeItem;

public abstract class TreeItemPopup extends ActionPopup {

    protected final AbstractTreeItem treeItem;
    protected final String type;

    protected TreeItemPopup(AbstractTreeItem treeItem, String type) {
        this.treeItem = treeItem;
        this.type = type;
    }

    /**
     * Creates the TextField for the TreeItemPopup-s with the lengthProperty listener
     * @return TextField with the length property listener
     */
    protected TextField addTextField() {
        var textField = new TextField();
        textField.lengthProperty().addListener((observable, oldValue, newValue) -> textFieldLengthListener(textField));
        return textField;
    }

    /**
     * Method to sort items into correct order after an action
     * @param abstractTreeItem - the treeItem what will call out its organizeView method
     */
    protected void sortItems(AbstractTreeItem abstractTreeItem) {
        abstractTreeItem.organizeView();
    }

    /**
     * Listener for setting the maximum length of character to project and task values
     * @param textField - the TextField to be listened to
     */
    private void textFieldLengthListener(TextField textField){
        int maxLength = 25;
        if (textField.getText().length() >= maxLength) {
            textField.setText(textField.getText().substring(0, maxLength));
        }
    }
}
