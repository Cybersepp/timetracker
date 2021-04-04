package logic.treeItems;

import gui.controllers.ProjectsTabController;
import gui.popups.CreateItemPopup;
import javafx.scene.control.*;

public abstract class AbstractTreeItem extends TreeItem<String> {

    private boolean archived = false;

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

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
            CreateItemPopup createItemPopup = new CreateItemPopup();
            createItemPopup.popup(this, "project");
        });
        return addProject;
    }

    private MenuItem createTask() {
        MenuItem addTask = new MenuItem("add task");
        addTask.setOnAction(e -> {
            CreateItemPopup createItemPopup = new CreateItemPopup();
            createItemPopup.popup(this,"task");
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

    protected MenuItem archive() {
        MenuItem archive = new MenuItem("archive project");
        archive.setOnAction(e -> {
            this.getParent().getChildren().remove(this);
            ProjectsTabController.getArchived().getChildren().add(this);

            this.setArchived(true);
            // TODO archived projects are unable to start recordings
            // TODO changing archived value for project, should also change value for tasks (might need to overwrite getChildren or do a List of children)
        });
        return archive;
    }

    protected MenuItem unArchive() {
        MenuItem unArchive = new MenuItem("unarchive");
        unArchive.setOnAction(e -> {
            this.getParent().getChildren().remove(this);
            ProjectsTabController.getProjects().getChildren().add(this);

            this.setArchived(false);
        });
        return unArchive;
    }
}
