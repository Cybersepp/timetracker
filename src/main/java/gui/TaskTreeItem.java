package gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class TaskTreeItem extends AbstractTreeItem {

    public TaskTreeItem(String name) {
        this.setValue(name);
    }

    @Override
    public ContextMenu getMenu() {
        MenuItem changeName = new MenuItem("change name");
        changeName.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // TODO set name to a new name
                // TODO find a way to change project / task name and keep it's data intact (tasks, time spent)
            }
        });

        MenuItem deleteTask = new MenuItem("delete task");
        deleteTask.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // TODO should send out a warning message if there are any recordings associated with the item
                // TODO should also delete all history that is associated with this item
                // TODO Maybe should have also delete shortcut?
            }
        });

        MenuItem markAsDone = new MenuItem("mark as done");
        markAsDone.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // TODO mark task as done and cross it out
                // TODO done tasks should not be able to add any recordings
                // TODO crossed out tasks should be at the end of the project's task list
            }
        });

        return new ContextMenu(changeName, deleteTask, markAsDone);
    }
}
