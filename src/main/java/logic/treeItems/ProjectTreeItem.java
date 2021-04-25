package logic.treeItems;

import data.FileAccess;
import gui.controllers.ProjectsTabController;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectTreeItem extends AbstractTreeItem {

    private List<TaskTreeItem> juniors = new ArrayList<>(); // Junior is just a word for the child object

    /**
     * Method that sets the project and all of its juniors/children to the archived state of the new root
     * @param newRoot either "archived root" or "active projects root" with type RootTreeItem
     */
    public void setArchived(RootTreeItem newRoot) {
        // TODO archived projects should be made unable to start recordings
        RootTreeItem formerParent = (RootTreeItem) this.getParent();
        formerParent.removeJunior(this);
        newRoot.addJunior(this);
        this.setArchived(newRoot.isArchived());
        for(TaskTreeItem task : this.getJuniors()) {
            task.setArchived(newRoot.isArchived());
        }
    }

    // -------------- DATA -----------------
    public List<TaskTreeItem> getJuniors() {
        return juniors;
    }

    // TODO Should we override List add and remove (and also quite many methods more) so the following two methods would not be needed?
    // TODO ProjectTreeItem and RootTreeItem should use the same method not two different ones. How can we do this?

    /**
     * Adds a task to the juniors Arraylist and also adds the task to the GUI TreeView children Observable list
     * @param junior - the task to be added
     */
    public void addJunior(TaskTreeItem junior) {
        this.juniors.add(junior);
        this.getChildren().add(junior);
    }

    /**
     * Removes a task from the juniors Arraylist and also removes the task from the GUI TreeView children Observable list
     * @param junior - the task to be removed
     */
    public void removeJunior(TaskTreeItem junior) {
        this.juniors.remove(junior);
        this.getChildren().remove(junior);
    }

    // ---------- Constructor for reading from file ------------------
    public ProjectTreeItem(String value, List<TaskTreeItem> juniors, boolean archived) {
        super(value);
        this.juniors = juniors;
        this.archived = archived;
    }

    // ---------- Constructor for creating a project ------------------
    public ProjectTreeItem(String value) {
        super(value);
    }

    // --------- GUI ------------

    /**
     * Creates a ContextMenuItem with archiving functionality
     * @return MenuItem with the needed functionality and text display
     */
    private MenuItem archive() {
        MenuItem archive = new MenuItem("Archive");
        archive.setOnAction(e -> setArchived(ProjectsTabController.getArchived()));
        FileAccess.saveData();
        return archive;
    }

    /**
     * Creates a ContextMenuItem with unarchiving functionality
     * @return MenuItem with the needed functionality and text display
     */
    private MenuItem unArchive() {
        MenuItem unArchive = new MenuItem("Unarchive");
        unArchive.setOnAction(e -> setArchived(ProjectsTabController.getProjects()));
        FileAccess.saveData();
        return unArchive;
    }

    /**
     * Creates a ContextMenu with the selected MenuItem-s depending on the archived state
     * @return ContextMenu to be viewed with the right click on the ProjectTreeItem
     */
    @Override
    public ContextMenu getMenu() {

        MenuItem changeName = changeName();
        MenuItem deleteProject = deleteItem("Delete project");

        if (isArchived()) {
            MenuItem unArchive = unArchive();
            return new ContextMenu(changeName, deleteProject, unArchive);
        }

        MenuItem addTask = createItem("task");
        MenuItem archive = archive();
        return new ContextMenu(addTask, changeName, deleteProject, archive);
    }

    /**
     * @return - the name of the project (Needed for ComboBox in createTaskPopup)
     */
    //TODO try to go around this method, i don't like this
    @Override
    public String toString() {
        return this.getValue();
    }

    /**
     * @return - the type of the AbstractTreeItem as a String
     */
    @Override
    public String toStringType() {
        return "project";
    }
}
