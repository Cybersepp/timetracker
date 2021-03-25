package gui.treeItems;

import javafx.scene.control.TreeCell;

public final class TreeCellImplication extends TreeCell<String> {

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(getItem() == null ? "" : getItem());
            setGraphic(getTreeItem().getGraphic());
            setContextMenu( ((AbstractTreeItem) getTreeItem()).getMenu());
        }
    }
}

