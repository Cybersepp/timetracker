package logic.treeItems;

import gui.popups.action.ChangeTaskProjectPopup;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;

import java.util.ArrayList;
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
    private MenuItem markAsDone() {
        MenuItem markAsDone = new MenuItem("Mark as done");
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

    private MenuItem moveToAnotherProject() {
        MenuItem moveToAnother = new MenuItem("Change project");
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
        MenuItem markAsDone = this.markAsDone();
        return new ContextMenu(changeName, deleteTask, markAsDone, moveToAnotherProject());
    }

    /**
     * @return - the type of the AbstractTreeItem as a String
     */
    @Override
    public String toStringType() {
        return "task";
    }
}
