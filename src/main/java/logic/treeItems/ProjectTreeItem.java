package logic.treeItems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import data.FileAccess;
import data.deserialization.ProjectItemDeserialization;
import gui.controllers.ProjectsTabController;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonIncludeProperties({"value", "archived", "tasks"})
@JsonDeserialize(using = ProjectItemDeserialization.class)
public class ProjectTreeItem extends AbstractTreeItem implements Comparable<ProjectTreeItem>, Serializable {

    @JsonProperty(value = "tasks")

    private List<TaskTreeItem> juniors = new ArrayList<>(); // Junior is just a word for the child object

    /**
     * Method that sets the project and all of its juniors/children to the archived state of the new root
     * @param newRoot either "archived root" or "active projects root" with type RootTreeItem
     */
    public void setArchived(RootTreeItem newRoot) {
        RootTreeItem formerParent = (RootTreeItem) this.getParent();
        formerParent.removeJunior(this);
        newRoot.addJunior(this);
        this.setArchived(newRoot.isArchived());
        for(TaskTreeItem task : this.getJuniors()) {
            task.setArchived(newRoot.isArchived());
        }
        this.getParentRoot().organizeView();
        FileAccess.saveData();
    }

    // -------------- DATA -----------------
    public List<TaskTreeItem> getJuniors() {
        return juniors;
    }

    public RootTreeItem getParentRoot() {
        return (RootTreeItem) this.getParent();
    }

    /**
     * Adds a task to the juniors Arraylist and also adds the task to the GUI TreeView children Observable list
     * @param junior - the task to be added
     */
    public void addJunior(TaskTreeItem junior) {
        this.juniors.add(junior);
        this.getChildren().add(junior);
    }

    public void addAllJuniors(Collection<TaskTreeItem> juniors) {
        for (TaskTreeItem task : juniors) {
            this.juniors.add(task);
            this.getChildren().add(task);
        }
    }

    /**
     * Removes a task from the juniors Arraylist and also removes the task from the GUI TreeView children Observable list
     * @param junior - the task to be removed
     */
    public void removeJunior(TaskTreeItem junior) {
        this.juniors.remove(junior);
        this.getChildren().remove(junior);
    }

    // ---------- Constructor ------------------
    public ProjectTreeItem(String value) {
        super(value);
    }


    public ProjectTreeItem(String value, boolean archived) {
        super(value);
        this.archived = archived;
    }
    // --------- GUI ------------

    /**
     * Creates a ContextMenuItem with archiving functionality
     * @return MenuItem with the needed functionality and text display
     */
    private MenuItem archive() {
        var archive = new MenuItem("Archive");
        archive.setOnAction(e -> setArchived(ProjectsTabController.getArchivedRoot()));
        return archive;
    }

    /**
     * Creates a ContextMenuItem with unarchiving functionality
     * @return MenuItem with the needed functionality and text display
     */
    private MenuItem unArchive() {
        var unArchive = new MenuItem("Unarchive");
        unArchive.setOnAction(e -> setArchived(ProjectsTabController.getActiveRoot()));
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
     * @return - the name of the project (Needed for ComboBox in ActionPopup)
     */
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

    /**
     * Method that organizes all the tasks in a way stated in the TaskTreeItem compareTo method
     */
    //TODO can you also sort ObservableList without removing and adding everything back?
    @Override
    public void organizeView() {
        this.getJuniors().sort(TaskTreeItem::compareTo);
        this.getChildren().removeAll(this.getChildren());
        this.getChildren().addAll(juniors);
    }

    /**
     * Method for comparing project names with each other alphabetically
     * @param o - comparable project
     * @return a negative integer, zero, or a positive integer as this project is less than, equal to, or greater than the specified project.
     */
    @Override
    public int compareTo(ProjectTreeItem o) {
        return this.getValue().compareTo(o.getValue());
    }
}
