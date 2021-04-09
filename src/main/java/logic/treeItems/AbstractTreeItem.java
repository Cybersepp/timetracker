package logic.treeItems;

import gui.popups.CreateItemPopup;
import javafx.scene.control.*;

public abstract class AbstractTreeItem extends TreeItem<String> {

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

    protected MenuItem changeName() {
        MenuItem changeName = new MenuItem("change name");
        changeName.setOnAction(e -> {
            this.setValue("Changed name");
            // TODO set name to a new name using popup
        });
        return changeName;
    }

    protected MenuItem createItem(String type) {
        if (type.equals("project")) return createProject();
        else return createTask();
    }

    private MenuItem createProject() {
        MenuItem addProject = new MenuItem("Create project");
        addProject.setOnAction(e -> {
            CreateItemPopup createItemPopup = new CreateItemPopup(this, "project");
            createItemPopup.popup();
        });
        return addProject;
    }

    private MenuItem createTask() {
        MenuItem addTask = new MenuItem("add task");
        addTask.setOnAction(e -> {
            CreateItemPopup createItemPopup = new CreateItemPopup(this, "task");
            createItemPopup.popup();
        });
        return addTask;
    }

    protected MenuItem deleteItem(String text) {
        MenuItem deleteTask = new MenuItem(text);
        deleteTask.setOnAction(e -> {
            this.getParent().getChildren().remove(this);
            // TODO should send out a warning message if there are any recordings associated with the item
            // TODO should also delete all history that is associated with this item
            // TODO Maybe should have also delete shortcut?
        });
        return deleteTask;
    }
}
