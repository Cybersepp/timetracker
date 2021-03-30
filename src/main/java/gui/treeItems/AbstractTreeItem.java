package gui.treeItems;

import gui.popups.CreateItemPopup;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;

public abstract class AbstractTreeItem extends TreeItem<String> {

    public abstract ContextMenu getMenu();

    protected MenuItem changeName() {
        MenuItem changeName = new MenuItem("change name");
        changeName.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // TODO set name to a new name
                // TODO find a way to change project / task name and keep it's data intact (tasks, time spent)
            }
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
        deleteTask.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // TODO should send out a warning message if there are any recordings associated with the item
                // TODO should also delete all history that is associated with this item
                // TODO Maybe should have also delete shortcut?
            }
        });
        return deleteTask;
    }

    protected MenuItem archive() {
        MenuItem archive = new MenuItem("archive project");
        archive.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // TODO replace active project to archived project without losing any daya
                // TODO archived projects are unable to start recordings
            }
        });
        return archive;
    }

    protected MenuItem unarchive() {
        MenuItem unarchive = new MenuItem("unarchive");
        unarchive.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // TODO replace archived project to active project without losing any data
                // TODO unarchived projects should have done tasks intact and all data intact
            }
        });
        return unarchive;
    }

    protected MenuItem markAsDone() {
        MenuItem markAsDone = new MenuItem("mark as done");
        markAsDone.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // TODO mark task as done and cross it out
                // TODO done tasks should not be able to add any recordings
                // TODO crossed out tasks should be at the end of the project's task list
            }
        });
        return markAsDone;
    }
}
