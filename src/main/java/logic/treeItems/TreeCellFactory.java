package logic.treeItems;

import data.FileAccess;
import gui.popups.notification.ErrorPopup;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Custom cell factory class for the treeItem to edit names and call out context menu
 */
public final class TreeCellFactory extends TreeCell<String> {

    private TextField textField;
    private final MenuItem rename = changeName();

    /**
     * Actions to perform when the user wants to edit a certain treeItem
     */
    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTextField();
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }

    /**
     * Actions to perform when the user cancels editing
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getItem());
        setGraphic(getTreeItem().getGraphic());
    }

    /**
     * Method for updating a treeItem by making the cell editable or by calling out a contextMenu
     *
     * @param item  - the new item for the cell
     * @param empty - whether or not this cell represents data from the list. If it is empty, then it does not represent any domain data, but is a cell being used to render an "empty" row.
     */
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            getTreeView().setEditable(getTreeItem().getClass() != RootTreeItem.class);

            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getTreeItem().getValue());
                }
                setText(null);
                setGraphic(textField);
            } else {
                updateContextMenu();
            }
        }
    }

    /**
     * Sets the right click onto an item in the projectsTab to display a ContextMenu
     */
    private void updateContextMenu() {
        if (getItem() == null) {
            setText("");
        } else {
            setText(getItem());
        }
        setGraphic(getTreeItem().getGraphic());
        setContextMenu(((AbstractTreeItem) getTreeItem()).getMenu());

        if (getTreeItem().getClass() == TaskTreeItem.class || getTreeItem().getClass() == ProjectTreeItem.class) {
            getContextMenu().getItems().add(rename);
        }

    }

    /**
     *
     */
    private void createTextField() {
        textField = new TextField(getTreeItem().getValue());
        textField.setOnKeyReleased(this::textFieldKeyReleased);
        textField.lengthProperty().addListener((observable, oldValue, newValue) -> textFieldLengthListener(textField));
    }

    /**
     * Method to set actions on key events
     *
     * @param event - key event (when a key is pressed)
     */
    private void textFieldKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            enterKeyEvent();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            cancelEdit();
        }
    }

    /**
     * Listener for setting the maximum length characters typed inside the TextField
     *
     * @param textField - the TextField to be listened to
     */
    private void textFieldLengthListener(TextField textField) {
        if (textField.getText().length() >= 25) {
            textField.setText(textField.getText().substring(0, 25));
        }
    }

    /**
     * Creates a ContextMenuItem with rename functionality
     *
     * @return MenuItem with the needed functionality and text display
     */
    private MenuItem changeName() {
        var changeName = new MenuItem("Rename");
        changeName.setOnAction(e -> getTreeView().edit(getTreeItem()));
        return changeName;
    }

    /**
     * Action to be performed after the key event with "Enter" has been activated
     */
    private void enterKeyEvent() {
        if (textField.getText().isEmpty()) {
            new ErrorPopup("You can't create an empty object!").popup();
            return;
        }
        final var sameName = getTreeItem().getParent().getChildren().stream()
                .filter(stringTreeItem -> stringTreeItem.getValue().equals(textField.getText().trim()))
                .map(TreeItem::getValue)
                .findFirst();
        if (sameName.isPresent() && !sameName.get().equals(getTreeItem().getValue())) {
            new ErrorPopup("An item with the chosen name already exists!").popup();
            return;
        }
        commitEdit(textField.getText());
        ((AbstractTreeItem) getTreeItem()).organizeView();
        FileAccess.saveData();
    }
}

