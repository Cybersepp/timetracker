package logic.treeItems;

import data.FileAccess;
import gui.popups.action.ChangeTaskProjectPopup;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskTreeItem extends AbstractTreeItem {

    private boolean done = false;
    private List<String> records = new ArrayList<>();

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public List<String> getRecords() {
        return records;
    }

    // ------------------ Constructor for creating a task while reading from file -------------------
    public TaskTreeItem(String value, boolean archived, boolean done, List<String> records) {
        super(value);
        this.archived = archived;
        this.done = done;
        this.records = records;
    }

    // ------------ Constructor for creating a task ---------------
    public TaskTreeItem(String value) {
        super(value);
    }

    // ------------------ GUI ----------------------
    /**
     * Creates a ContextMenuItem with the functionality to mark the task as done
     * @return MenuItem with the needed functionality and text display
     */
    private MenuItem markAsDone(String label) {
        // TODO mark task as done and cross it out
        var markAsDone = new MenuItem(label);
        markAsDone.setOnAction(e -> {
            if (isDone()) {
                this.setValue(this.getValue().substring(5));
                this.setDone(false);
                reOrganizeTasks(true);
            }
            else {
                this.setValue("Done " + this.getValue());
                this.setDone(true);
                reOrganizeTasks(false);
            }
            FileAccess.saveData();
        });
        return markAsDone;
    }

    private MenuItem moveToAnotherProject() {
        var moveToAnother = new MenuItem("Change project");
        moveToAnother.setOnAction(event -> new ChangeTaskProjectPopup(this, toStringType()).popup());
        return moveToAnother;
    }

    /**
     * Creates a ContextMenu with the selected MenuItem-s depending on the archived state
     * @return ContextMenu to be viewed with the right click on the ProjectTreeItem
     */
    @Override
    public ContextMenu getMenu() {

        MenuItem changeName = this.changeName();
        MenuItem deleteTask = this.deleteItem("Delete task");
        if (isArchived()) {
            return new ContextMenu(changeName, deleteTask);

        }
        MenuItem markAsDone;
        if (this.isDone()) {
            markAsDone = this.markAsDone("Complete task");
        }
        else {
            markAsDone = this.markAsDone("Reactivate task");
        }

        return new ContextMenu(changeName, deleteTask, markAsDone, moveToAnotherProject());
    }

    private void reOrganizeTasks(boolean toBeginning) {
        List<TaskTreeItem> tasks = this.getParentProject().getJuniors();
        int taskPosition = -1;
        for (int i = 0; i < tasks.size(); i++) {
            if (this == tasks.get(i)) {
                taskPosition = i;
                break;
            }
        }

        if (taskPosition == -1 || this.getParent().getChildren().get(taskPosition) != this) {
            return;
        }
        swapTasks(toBeginning, taskPosition, tasks);
    }

    private void swapTasks(boolean toBeginning, int taskPosition, List<TaskTreeItem> tasks) {
        if (toBeginning) {
            for (int i = taskPosition-1; i >= 0; i--) {
                if (!tasks.get(i).isDone()) {
                    break;
                }
                Collections.swap(this.getParentProject().getJuniors(), i, taskPosition);
                Collections.swap(this.getParent().getChildren(), i, taskPosition);
                taskPosition = i;
            }
        }
        else {
            for (int i = taskPosition+1; i < tasks.size(); i++) {
                if (tasks.get(i).isDone()) {
                    break;
                }
                Collections.swap(this.getParentProject().getJuniors(), i, taskPosition);
                Collections.swap(this.getParent().getChildren(), i, taskPosition);
                taskPosition = i;
            }
        }
    }

    public ProjectTreeItem getParentProject() {
        return (ProjectTreeItem) this.getParent();
    }

    /**
     * @return - the type of the AbstractTreeItem as a String
     */
    @Override
    public String toStringType() {
        return "task";
    }
}
