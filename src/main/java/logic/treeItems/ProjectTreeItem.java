package logic.treeItems;

import data.DataHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProjectTreeItem extends AbstractTreeItem {

    private List<TaskTreeItem> juniors = new ArrayList<>(); // Junior is just a word for the child object
    private final LocalDateTime creationDate;

    // TODO add list of children and other fields so the data.Project and data.Task classes would not be needed

    // -------------- DATA -----------------
    public List<TaskTreeItem> getJuniors() {
        return juniors;
    }

    // TODO Should we override List add and remove (and also quite many methods more) so the following two methods would not be needed?
    public void addJunior(TaskTreeItem junior) {
        this.juniors.add(junior);
        this.getChildren().add(junior);
    }

    public void removeJunior(TaskTreeItem junior) {
        this.juniors.remove(junior);
        this.getChildren().remove(junior);
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    // ---------- Constructor for reading from file ------------------
    public ProjectTreeItem(String value, List<TaskTreeItem> juniors, LocalDateTime creationDate, boolean archived) {
        super(value);
        this.juniors = juniors;
        this.creationDate = creationDate;
        this.archived = archived;
    }

    // ---------- Constructor for creating a project ------------------
    public ProjectTreeItem(String value) {
        super(value);
        this.creationDate = LocalDateTime.now();
    }

    // --------- GUI ------------
    @Override
    public ContextMenu getMenu() {

        MenuItem changeName = changeName();
        MenuItem deleteProject = deleteItem("delete project");

        if (isArchived()) {
            MenuItem unArchive = unArchive();
            return new ContextMenu(changeName, deleteProject, unArchive);
        }

        MenuItem addTask = createItem("task");
        MenuItem archive = archive();
        return new ContextMenu(addTask, changeName, deleteProject, archive);
    }
}
