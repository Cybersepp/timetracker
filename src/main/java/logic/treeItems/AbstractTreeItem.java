package logic.treeItems;

import gui.popups.action.treeItemAction.ChangeNamePopup;
import gui.popups.action.treeItemAction.CreateItemPopup;
import javafx.scene.Node;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import logic.commands.delete.DeleteProjectCommand;
import logic.commands.delete.DeleteTaskCommand;

public abstract class AbstractTreeItem extends CheckBoxTreeItem<String> {

    protected boolean archived = false;

    protected AbstractTreeItem(String value) {
        this.setValue(value);
    }

    protected AbstractTreeItem(String value, Node node) {
        super(value, node);
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
     * Creates a ContextMenuItem with rename functionality
     * @return MenuItem with the needed functionality and text display
     */
    protected MenuItem changeName() {
        var changeName = new MenuItem("Rename");
        changeName.setOnAction(e -> {
            var changeNamePopup = new ChangeNamePopup(this);
            changeNamePopup.popup();
        });
        return changeName;
    }

    /**
     * Creates a ContextMenuItem with the create item functionality
     * @param type the type of the item to be created
     * @return MenuItem with the needed functionality and text display
     */
    protected MenuItem createItem(String type) {
        if (type.equals("project")) return createProject();
        else return createTask();
    }

    /**
     * Creates a ContextMenuItem with create project functionality
     * @return MenuItem with the needed functionality and text display
     */
    private MenuItem createProject() {
        var addProject = new MenuItem("Create project");
        addProject.setOnAction(e -> {
            var createItemPopup = new CreateItemPopup(this, "project");
            createItemPopup.popup();
        });
        return addProject;
    }

    /**
     * Creates a ContextMenuItem with create task functionality
     * @return MenuItem with the needed functionality and text display
     */
    private MenuItem createTask() {
        var addTask = new MenuItem("Add task");
        addTask.setOnAction(e -> {
            var createItemPopup = new CreateItemPopup(this, "task");
            createItemPopup.popup();
        });
        return addTask;
    }

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
