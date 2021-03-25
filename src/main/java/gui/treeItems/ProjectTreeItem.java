package gui.treeItems;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class ProjectTreeItem extends AbstractTreeItem {

    public ProjectTreeItem(String name) {
        this.setValue(name);
    }

    @Override
    public ContextMenu getMenu() {
        MenuItem addTask = createItem("task");
        MenuItem changeName = changeName();
        MenuItem deleteProject = deleteItem("delete project");
        MenuItem archive = archive();
        return new ContextMenu(addTask, changeName, deleteProject, archive);
    }
}
