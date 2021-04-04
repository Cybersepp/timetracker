package logic.treeItems;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class ProjectTreeItem extends AbstractTreeItem {

    // TODO add list of children and other fields so the data.Project and data.Task classes would not be needed

    public ProjectTreeItem(String name) {
        this.setValue(name);
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
