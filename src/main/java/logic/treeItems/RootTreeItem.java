package logic.treeItems;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class RootTreeItem extends AbstractTreeItem {

    private List<ProjectTreeItem> juniors = new ArrayList<>();

    public List<ProjectTreeItem> getJuniors() {
        return juniors;
    }

    /**
     * Adds a project to the juniors Arraylist and also adds the project to the GUI TreeView children Observable list
     * @param junior - the project to be added
     */
    public void addJunior(ProjectTreeItem junior) {
        this.juniors.add(junior);
        this.getChildren().add(junior);
    }

    /**
     * Removes a project from the juniors Arraylist and also removes the project from the GUI TreeView children Observable list
     * @param junior - the project to be removed
     */
    public void removeJunior(ProjectTreeItem junior) {
        this.juniors.remove(junior);
        this.getChildren().remove(junior);
    }

    // ------------ Constructors -------------------
    public RootTreeItem(String value) {
        super(value);
    }

    public RootTreeItem(String value, boolean archived) {
        // TODO this constructor is to be deleted after the JSON has the archived status inside it also
        super(value);
        this.archived = archived;
    }

    public RootTreeItem(String value, List<ProjectTreeItem> children) {
        // TODO is this constructor also necessary?
        super(value);
        this.juniors = children;
    }

    // ------------ GUI -------------------
    /**
     * Creates a ContextMenu with the selected MenuItem-s depending on the archived state
     * @return ContextMenu to be viewed with the right click on the ProjectTreeItem
     */
    @Override
    public ContextMenu getMenu() {

        if (isArchived()) {
            return new ContextMenu();
        }
        MenuItem addProject = this.createItem("project");
        return new ContextMenu(addProject);
    }

    /**
     * @return - the type of the AbstractTreeItem as a String
     */
    @Override
    public String toStringType() {
        return "root";
    }

    @Override
    public void organizeView() {
        // Doesn't need to do anything
    }
}
