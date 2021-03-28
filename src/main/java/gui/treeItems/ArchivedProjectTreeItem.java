package gui.treeItems;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class ArchivedProjectTreeItem extends AbstractTreeItem{

    public ArchivedProjectTreeItem(String name) {
        this.setValue(name);
    }

    @Override
    public ContextMenu getMenu() {
        MenuItem changeName = changeName();
        MenuItem deleteProject = deleteItem("delete project");
        MenuItem unarchive = unarchive();
        return new ContextMenu(changeName, deleteProject, unarchive);
    }
}
