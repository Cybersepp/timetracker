package gui;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;

public abstract class AbstractTreeItem extends TreeItem {

    public abstract ContextMenu getMenu();
}
