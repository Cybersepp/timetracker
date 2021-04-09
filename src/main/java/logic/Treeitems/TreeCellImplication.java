package logic.Treeitems;

import javafx.scene.control.TreeCell;

public final class TreeCellImplication extends TreeCell<String> {

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (getItem() == null) {
                setText("");
            } else {
                setText(getItem());
            }
            setGraphic(getTreeItem().getGraphic());
            setContextMenu( ((AbstractTreeItem) getTreeItem()).getMenu());
        }
    }
}

