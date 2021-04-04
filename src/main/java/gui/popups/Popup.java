package gui.popups;

import logic.treeItems.AbstractTreeItem;

public interface Popup {

    void popup(AbstractTreeItem treeItem, String type);
}
