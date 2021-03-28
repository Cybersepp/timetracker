package gui.treeItems;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class TaskTreeItem extends AbstractTreeItem {

    public TaskTreeItem(String name) {
        this.setValue(name);
    }

    @Override
    public ContextMenu getMenu() {

        MenuItem changeName = this.changeName();
        MenuItem deleteTask = this.deleteItem("delete task");
        MenuItem markAsDone = this.markAsDone();
        return new ContextMenu(changeName, deleteTask, markAsDone);
    }
}
