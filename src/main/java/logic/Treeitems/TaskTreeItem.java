package logic.Treeitems;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskTreeItem extends AbstractTreeItem {

    private boolean done = false;
    private final LocalDateTime creationDate;
    private List<String> records = new ArrayList<>();

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<String> getRecords() {
        return records;
    }

    // ------------------ Constructor for creating a task while reading from file -------------------
    public TaskTreeItem(String value, LocalDateTime creationDate, boolean archived, boolean done, List<String> records) {
        super(value);
        this.creationDate = creationDate;
        this.archived = archived;
        this.done = done;
        this.records = records;
    }

    // ------------ Constructor for creating a task ---------------
    public TaskTreeItem(String value) {
        super(value);
        this.creationDate = LocalDateTime.now();
    }

    // ------------------ GUI ----------------------
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