package logic.Treeitems;

import gui.popups.ChangeNamePopup;
import gui.popups.CreateItemPopup;
import gui.popups.WarningPopup;
import javafx.scene.control.*;
import logic.commands.DeleteProjectCommand;
import logic.commands.DeleteTaskCommand;

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

    protected MenuItem changeName() {
        MenuItem changeName = new MenuItem("Rename");
        changeName.setOnAction(e -> {
            var changeNamePopup = new ChangeNamePopup(this);
            changeNamePopup.popup();
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
        MenuItem addTask = new MenuItem("Add task");
        addTask.setOnAction(e -> {
            CreateItemPopup createItemPopup = new CreateItemPopup(this, "task");
            createItemPopup.popup();
        });
        return addTask;
    }

    protected MenuItem deleteItem(String text) {
        MenuItem deleteTask = new MenuItem(text);
        deleteTask.setOnAction(e -> deleteAction());
        return deleteTask;
    }

    private void deleteAction() {
        if (this.getClass().equals(ProjectTreeItem.class)) {
            new DeleteProjectCommand((ProjectTreeItem) this).commandControl();
        }
        else if (this.getClass().equals(TaskTreeItem.class)) {
            new DeleteTaskCommand((TaskTreeItem) this).commandControl();
        }
    }
}