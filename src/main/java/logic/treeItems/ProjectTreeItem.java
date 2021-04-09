package logic.treeItems;

import gui.controllers.ProjectsTabController;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProjectTreeItem extends AbstractTreeItem {

    private List<TaskTreeItem> juniors = new ArrayList<>(); // Junior is just a word for the child object
    private final LocalDateTime creationDate;

    public void setArchived(RootTreeItem newRoot) {
        // TODO archived projects should be made unable to start recordings
        RootTreeItem formerParent = (RootTreeItem) this.getParent();
        formerParent.removeJunior(this);
        newRoot.addJunior(this);

        this.setArchived(newRoot.isArchived());
        for(TaskTreeItem task : this.getJuniors()) {
            task.setArchived(newRoot.isArchived());
        }
    }

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

    private MenuItem archive() {
        MenuItem archive = new MenuItem("archive project");
        archive.setOnAction(e -> setArchived(ProjectsTabController.getArchived()));
        return archive;
    }

    private MenuItem unArchive() {
        MenuItem unArchive = new MenuItem("unarchive");
        unArchive.setOnAction(e -> setArchived(ProjectsTabController.getProjects()));
        return unArchive;
    }

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
