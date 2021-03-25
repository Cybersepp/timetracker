package gui.treeItems;

import javafx.scene.control.ContextMenu;

public class ArchivedRootTreeItem extends AbstractTreeItem{

    public ArchivedRootTreeItem(String name) {
        this.setValue(name);
    }

    @Override
    public ContextMenu getMenu() {
        return new ContextMenu();
    }
}
