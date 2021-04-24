package logic.treeItems;

import javafx.scene.control.TreeCell;

public final class TreeCellFactory extends TreeCell<String> {

    /**
     * Method for making a custom cell factory
     * @param item - the new item for the cell
     * @param empty - whether or not this cell represents data from the list. If it is empty, then it does not represent any domain data, but is a cell being used to render an "empty" row.
     */
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

    /**
     * Sets the right click onto an item in the projectsTab to display a ContextMenu
     */
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

