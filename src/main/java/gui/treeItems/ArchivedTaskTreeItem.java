package gui.treeItems;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class ArchivedTaskTreeItem extends AbstractTreeItem{

    public ArchivedTaskTreeItem(String name) {
        this.setValue(name);
    }

    @Override
    public ContextMenu getMenu() {
        MenuItem changeName = changeName();
        MenuItem deleteTask = deleteItem("delete task");
        return new ContextMenu(changeName, deleteTask);
    }
}
