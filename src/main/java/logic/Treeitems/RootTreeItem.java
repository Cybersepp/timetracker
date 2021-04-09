package logic.Treeitems;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class RootTreeItem extends AbstractTreeItem {

    private List<ProjectTreeItem> juniors = new ArrayList<>();

    public List<ProjectTreeItem> getJuniors() {
        return juniors;
    }

    // TODO Should we override List add and remove (and also quite many methods more) so the following two methods would not be needed?
    // TODO add method for adding a List of projects at once?
    // TODO ProjectTreeItem and RootTreeItem should use the same method not two different ones. How can we do this? Extends another class?
    public void addJunior(ProjectTreeItem junior) {
        this.juniors.add(junior);
        this.getChildren().add(junior);
    }

    public void removeJunior(ProjectTreeItem junior) {
        this.juniors.remove(junior);
        this.getChildren().remove(junior);
    }

    public RootTreeItem(String value) {
        super(value);
    }

    public RootTreeItem(String value, boolean archived) {
        super(value);
        this.archived = archived;
    }

    public RootTreeItem(String value, List<ProjectTreeItem> children) { // TODO is this constructor necessary?
        super(value);
        this.juniors = children;
    }

    // ------------ GUI -------------------
    @Override
    public ContextMenu getMenu() {

        if (isArchived()) {
            return new ContextMenu();
        }
        MenuItem addProject = this.createItem("project");
        return new ContextMenu(addProject);
    }

}
