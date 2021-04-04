package logic.treeItems;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class TaskTreeItem extends AbstractTreeItem {

    private boolean done = false;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public TaskTreeItem(String name) {
        this.setValue(name);
    }

    private MenuItem markAsDone() {
        MenuItem markAsDone = new MenuItem("mark as done");
        markAsDone.setOnAction(e -> {
            if (isDone()) {
                this.setValue(this.getValue().substring(5));
                this.setDone(false);
            }
            else {
                this.setValue("Done " + this.getValue());
                this.setDone(true);
            }
            // TODO mark task as done and cross it out
            // TODO done tasks should not be able to add any recordings
            // TODO crossed out tasks should be at the end of the project's task list
        });
        return markAsDone;
    }

    @Override
    public ContextMenu getMenu() {

        MenuItem changeName = this.changeName();
        MenuItem deleteTask = this.deleteItem("delete task");
        if (isArchived()) {
            return new ContextMenu(changeName, deleteTask);

        }
        MenuItem markAsDone = this.markAsDone();
        return new ContextMenu(changeName, deleteTask, markAsDone);
    }
}
