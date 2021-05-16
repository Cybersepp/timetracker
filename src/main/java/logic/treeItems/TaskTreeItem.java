package logic.treeItems;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import data.FileAccess;
import data.Recording;
import data.deserialization.TaskItemDeserialization;
import gui.icons.TaskIcon;
import gui.icons.TaskImageView;
import gui.popups.action.treeItemAction.ChangeTaskProjectPopup;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonIncludeProperties({"value", "done", "recordings"})
@JsonDeserialize(using = TaskItemDeserialization.class)
public class TaskTreeItem extends AbstractTreeItem implements Comparable<TaskTreeItem>, Serializable {

    private boolean done = false;
    private final List<Recording> recordings = new ArrayList<>();

    @JsonIgnore
    private final TaskIcon icon;

    public boolean isDone() {
        return done;
    }

    public List<Recording> getRecordings() {
        return recordings;
    }

    public ProjectTreeItem getParentProject() {
        return (ProjectTreeItem) this.getParent();
    }

    // ---------------- CONSTRUCTORS --------------------
    public TaskTreeItem(String value) {
        super(value, new TaskIcon(false).getImageView());
        icon = ((TaskImageView) this.getGraphic()).getIcon();
    }

    public TaskTreeItem(String value, boolean done) {
        super(value, new TaskIcon(done).getImageView());
        this.done = done;
        icon = ((TaskImageView) this.getGraphic()).getIcon();
    }

    /**
     * Adds a collection of recordings to the task recordings list
     * @param recordings - collection of recordings to add when reading from file
     */
    public void addAllRecordings(Collection<Recording> recordings) {
        for (Recording recording : recordings) {
            getRecordings().add(recording);
            recording.setParentTask(this);
        }
    }

    // ------------------ GUI ----------------------
    /**
     * Creates a ContextMenuItem with the functionality to mark the task as done
     * @return MenuItem with the needed functionality and text display
     */
    private MenuItem markAsDone(String label) {
        var markAsDone = new MenuItem(label);
        markAsDone.setOnAction(e -> {
            done = !done;
            icon.setIcon(done);
            this.organizeView();
            FileAccess.saveData();
        });
        return markAsDone;
    }

    /**
     * Creates a ContextMenuItem with the functionality to move a task from one project to another
     * @return MenuItem with the needed functionality
     */
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

        MenuItem deleteTask = this.deleteItem("Delete task");
        if (isArchived()) {
            return new ContextMenu(deleteTask);

        }
        MenuItem markAsDone;
        if (this.isDone()) {
            markAsDone = this.markAsDone("Reactivate task");
        }
        else {
            markAsDone = this.markAsDone("Complete task");
        }

        return new ContextMenu(deleteTask, markAsDone, moveToAnotherProject());
    }

    /**
     * @return - the name of the task (Needed for ComboBox)
     */
    @Override
    public String toString() {
        return this.getValue();
    }

    /**
     * @return - the type of the AbstractTreeItem as a String
     */
    @Override
    public String toStringType() {
        return "task";
    }
  
    /**
     * Method that sorts all Tasks in a way stated in the compareTo method
     */
    @Override
    public void organizeView() {
        //sorts tasks
        var project = this.getParentProject();
        project.getJuniors().sort(TaskTreeItem::compareTo);
        project.getChildren().removeAll(project.getChildren());
        project.getChildren().addAll(project.getJuniors());
    }

    /**
     * Method for comparing task names with each other alphabetically and also with the "done" state
     * @param o - comparable task
     * @return a negative integer, zero, or a positive integer as this task is less than, equal to, or greater than the specified task.
     */
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
