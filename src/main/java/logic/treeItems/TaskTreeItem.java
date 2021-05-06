package logic.treeItems;

import data.FileAccess;
import data.Recording;
import gui.popups.action.ChangeTaskProjectPopup;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class TaskTreeItem extends AbstractTreeItem implements Comparable<TaskTreeItem>{

    private boolean done = false;
    private List<Recording> recordings = new ArrayList<>();

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public List<Recording> getRecordings() {
        return recordings;
    }

    public ProjectTreeItem getParentProject() {
        return (ProjectTreeItem) this.getParent();
    }

    // ------------------ Constructor for creating a task while reading from file -------------------
    public TaskTreeItem(String value, boolean archived, boolean done, List<Recording> recordings) {
        super(value);
        this.archived = archived;
        this.done = done;
        this.recordings = recordings;
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
            }
            else {
                this.setValue("Done " + this.getValue());
                this.setDone(true);
            }
            this.organizeView();
            FileAccess.saveData();
        });
        return markAsDone;
    }

    private MenuItem moveToAnotherProject() {
        var moveToAnother = new MenuItem("Change project");
        moveToAnother.setOnAction(event -> {
            new ChangeTaskProjectPopup(this, toStringType()).popup();
            this.organizeView();
        });
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
            markAsDone = this.markAsDone("Reactivate task");
        }
        else {
            markAsDone = this.markAsDone("Complete task");
        }

        return new ContextMenu(changeName, deleteTask, markAsDone, moveToAnotherProject());
    }

    /**
     * @return - the type of the AbstractTreeItem as a String
     */
    @Override
    public String toStringType() {
        return "task";
    }

    @Override
    public void organizeView() {
        //TODO can you also sort ObservableList without removing and adding everything back?
        this.getParentProject().getJuniors().sort(TaskTreeItem::compareTo);
        var projectTreeItemJuniors = this.getParentProject().getJuniors();
        var treeViewProjectTasks = this.getParent().getChildren();
        treeViewProjectTasks.removeAll(this.getParent().getChildren());
        treeViewProjectTasks.addAll(projectTreeItemJuniors);
    }

    @Override
    public int compareTo(TaskTreeItem o) {
        if (this.isDone() && o.isDone() || !this.isDone() && !o.isDone()) {
            return this.getValue().compareTo(o.getValue());
        }
        if (this.isDone()) {
            return 1;
        }
        else {
            return -1;
        }
    }
}
