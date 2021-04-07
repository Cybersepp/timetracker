package logic.treeItems;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RootTreeItem extends AbstractTreeItem {

    private List<ProjectTreeItem> children = new ArrayList<>();

    public RootTreeItem(String value) {
        super(value);
    }

    public RootTreeItem(String value, List<ProjectTreeItem> children) {
        super(value);
        this.children = children;
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
