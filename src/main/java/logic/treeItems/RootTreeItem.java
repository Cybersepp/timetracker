package logic.treeItems;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class RootTreeItem extends AbstractTreeItem {

    public RootTreeItem(String name) {
        this.setValue(name);
    }

    @Override
    public ContextMenu getMenu() {

        if (isArchived()) {
            return new ContextMenu();
        }
        MenuItem addProject = this.createItem("project");
        return new ContextMenu(addProject);
    }

}
