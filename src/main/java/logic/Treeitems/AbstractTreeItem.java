package logic.Treeitems;

import gui.popups.ChangeNamePopup;
import gui.popups.CreateItemPopup;
import gui.popups.WarningPopup;
import javafx.scene.control.*;
import logic.Treeitems.commands.DeleteProjectCommand;
import logic.Treeitems.commands.DeleteTaskCommand;

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

            if (this.getChildren().isEmpty()) {
                new DeleteProjectCommand((ProjectTreeItem) this).command();
            }
            else {
                var warningPopup = new WarningPopup("Are you sure you want to delete this project and all of its data?", new DeleteProjectCommand((ProjectTreeItem) this));
                warningPopup.popup();
            }
        }
        else if (this.getClass().equals(TaskTreeItem.class)) {
            if (((TaskTreeItem) this).getRecords().isEmpty()) {
                new DeleteTaskCommand((TaskTreeItem) this).command();
            }
            else {
                var warningPopup = new WarningPopup("Are you sure you want to delete this task and all of its records?", new DeleteTaskCommand((TaskTreeItem) this));
                warningPopup.popup();
            }
        }
        // TODO Maybe should have also a shortcut for delete (del key)?
    }
}
