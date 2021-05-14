package logic.treeItems;

import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import logic.commands.delete.DeleteProjectCommand;
import logic.commands.delete.DeleteTaskCommand;

public abstract class AbstractTreeItem extends CheckBoxTreeItem<String> {

    protected boolean archived = false;

    protected AbstractTreeItem(String value) {
        this.setValue(value);
    }

    protected AbstractTreeItem(String value, ImageView imageView) {
        super(value, imageView);
    }

    public boolean isArchived() {
        return archived;
    }

    protected void setArchived(boolean archived) {
        this.archived = archived;
    }

    // ---------------- GUI and interactive methods ------------------------
    public abstract ContextMenu getMenu();

    /**
     * Creates a ContextMenuItem with delete functionality
     * @param text - the name of the function
     * @return MenuItem with the needed functionality and text display
     */
    protected MenuItem deleteItem(String text) {
        var deleteTask = new MenuItem(text);
        deleteTask.setOnAction(e -> deleteAction());
        return deleteTask;
    }

    /**
     * Delete function which decides on the Command that should be used in the current situation
     */
    private void deleteAction() {
        if (this.getClass().equals(ProjectTreeItem.class)) {
            new DeleteProjectCommand((ProjectTreeItem) this).commandControl();
        }
        else if (this.getClass().equals(TaskTreeItem.class)) {
            new DeleteTaskCommand((TaskTreeItem) this).commandControl();
        }
    }

    /**
     * @return String type of the treeItem
     */
    public abstract String toStringType();

    /**
     * Method that organizes the TreeView items in a certain order
     */
    public abstract void organizeView();
}
