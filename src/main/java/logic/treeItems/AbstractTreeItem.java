package logic.treeItems;

import gui.popups.action.ChangeNamePopup;
import gui.popups.action.CreateItemPopup;
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
        MenuItem changeName = new MenuItem("Rename");
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
        MenuItem addProject = new MenuItem("Create project");
        addProject.setOnAction(e -> {
            CreateItemPopup createItemPopup = new CreateItemPopup(this, "project");
            createItemPopup.popup();
        });
        return addProject;
    }

    /**
     * Creates a ContextMenuItem with create task functionality
     * @return MenuItem with the needed functionality and text display
     */
    private MenuItem createTask() {
        MenuItem addTask = new MenuItem("Add task");
        addTask.setOnAction(e -> {
            CreateItemPopup createItemPopup = new CreateItemPopup(this, "task");
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
        MenuItem deleteTask = new MenuItem(text);
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
}
