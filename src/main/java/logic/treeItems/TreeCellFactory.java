package logic.treeItems;

import javafx.scene.control.TreeCell;
import logic.CustomHBox;

public final class TreeCellFactory extends TreeCell<String> {

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            updateContextMenu();
        }

    }

    private void updateContextMenu() {
        if (getItem() == null) {
            setText("");
        } else {
            setText(getItem());
        }
        setGraphic(getTreeItem().getGraphic());
        setContextMenu( ((AbstractTreeItem) getTreeItem()).getMenu());
    }
}

