package logic.treeItems;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProjectTreeItem extends AbstractTreeItem {

    private List<TaskTreeItem> children = new ArrayList<>();
    private final LocalDateTime creationDate;

    // TODO add list of children and other fields so the data.Project and data.Task classes would not be needed

    public ProjectTreeItem(String value) {
        super(value);
        this.creationDate = LocalDateTime.now();
    }

    public ProjectTreeItem(String value, List<TaskTreeItem> children, LocalDateTime creationDate, boolean archived) {
        super(value);
        this.children = children;
        this.creationDate = creationDate;
        this.archived = archived;
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
