package gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class ProjectTreeItem extends AbstractTreeItem {

    public ProjectTreeItem(String name) {
        this.setValue(name);
    }

    @Override
    public ContextMenu getMenu() {
        MenuItem addTask = new MenuItem("add task");
        addTask.setOnAction(new EventHandler() {
            public void handle(Event t) {
                TaskTreeItem newTask = new TaskTreeItem("pick cotton");
                getChildren().add(newTask);
            }
        });

        MenuItem changeName = new MenuItem("change name");
        changeName.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // TODO set name to a new name
                // TODO find a way to change project / task name and keep it's data intact (tasks, time spent)
            }
        });

        MenuItem deleteProject = new MenuItem("delete project");
        deleteProject.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // TODO should send out a warning message if there are any recordings associated with the item
                // TODO should also delete all history that is associated with this item
                // TODO Maybe should have also delete shortcut?
            }
        });

        MenuItem archive = new MenuItem("archive");
        archive.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // TODO replace active project to archived project without losing any daya
                // TODO archived projects are unable to start recordings
                // TODO check if the project is a project and not a task?
            }
        });

        return new ContextMenu(addTask, changeName, deleteProject, archive);
    }
}
