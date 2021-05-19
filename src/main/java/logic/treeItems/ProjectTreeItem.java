package logic.treeItems;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import data.FileAccess;
import data.Recording;
import data.deserialization.ProjectItemDeserialization;
import gui.controllers.MainController;
import gui.controllers.ProjectsTabController;
import gui.icons.ProjectImageView;
import gui.icons.ProjectIcon;
import gui.popups.action.treeItemAction.CreateItemPopup;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@JsonIncludeProperties({"value", "color", "archived", "tasks"})
@JsonDeserialize(using = ProjectItemDeserialization.class)
public class ProjectTreeItem extends AbstractTreeItem implements Comparable<ProjectTreeItem>, Serializable {

    @JsonProperty(value = "tasks")
    private final List<TaskTreeItem> juniors = new ArrayList<>(); // Junior is just a word for the child object

    @JsonIgnore
    private final ProjectIcon icon;

    /**
     * Method that sets the project and all of its juniors/children to the archived state of the new root
     *
     * @param newRoot either "archived root" or "active projects root" with type RootTreeItem
     */
    public void setArchived(RootTreeItem newRoot) {
        RootTreeItem formerParent = this.getParentRoot();
        formerParent.removeJunior(this);
        newRoot.addJunior(this);
        setArchived(newRoot.isArchived());
        this.getParentRoot().organizeView();
        FileAccess.saveData();
    }

    @Override
    public void setArchived(boolean archived) {
        this.archived = archived;
        for (TaskTreeItem task : this.getJuniors()) {
            task.setArchived(archived);
        }
    }

    // -------------- DATA -----------------
    public List<TaskTreeItem> getJuniors() {
        return juniors;
    }

    public RootTreeItem getParentRoot() {
        return (RootTreeItem) this.getParent();
    }

    @JsonProperty(value = "color")
    public String getColor() {
        return icon.getCurrentColor();
    }

    /**
     * Adds a task to the juniors Arraylist and also adds the task to the GUI TreeView children Observable list
     *
     * @param junior - the task to be added
     */
    public void addJunior(TaskTreeItem junior) {
        this.juniors.add(junior);
        this.getChildren().add(junior);
    }

    /**
     * Adds a collection of tasks to the juniors list (used in reading from file)
     *
     * @param juniors - Collection of child tasks
     */
    public void addAllJuniors(Collection<TaskTreeItem> juniors) {
        for (TaskTreeItem task : juniors) {
            this.juniors.add(task);
            this.getChildren().add(task);
        }
    }

    /**
     * Removes a task from the juniors Arraylist and also removes the task from the GUI TreeView children Observable list
     *
     * @param junior - the task to be removed
     */
    public void removeJunior(TaskTreeItem junior) {
        this.juniors.remove(junior);
        this.getChildren().remove(junior);
    }

    // ---------- Constructors ------------------
    public ProjectTreeItem(String value) {
        super(value, new ProjectIcon().getImageView());
        icon = ((ProjectImageView) this.getGraphic()).getIcon();
    }

    public ProjectTreeItem(String value, ProjectIcon projectIcon) {
        super(value, projectIcon.getImageView());
        icon = projectIcon;
    }

    // --------- GUI ------------
    /**
     * Creates a ContextMenuItem with archiving functionality
     *
     * @return MenuItem with the needed functionality and text display
     */
    private MenuItem archive() {
        var archive = new MenuItem("Archive");
        archive.setOnAction(e -> setArchived(ProjectsTabController.getArchivedRoot()));
        return archive;
    }

    /**
     * Creates a ContextMenuItem with unarchiving functionality
     *
     * @return MenuItem with the needed functionality and text display
     */
    private MenuItem unArchive() {
        var unArchive = new MenuItem("Unarchive");
        unArchive.setOnAction(e -> setArchived(ProjectsTabController.getActiveRoot()));
        return unArchive;
    }

    /**
     * Creates a ContextMenuItem with create task functionality
     *
     * @return MenuItem with the needed functionality and text display
     */
    private MenuItem createTask() {
        var createTask = new MenuItem("Add task");
        createTask.setOnAction(e -> {
            var createItemPopup = new CreateItemPopup(this, "task");
            createItemPopup.popup();
        });
        return createTask;
    }

    /**
     * Creates a ContextMenuItem with create task functionality
     *
     * @return MenuItem with the needed functionality and text display
     */
    private MenuItem nextColor() {
        var nextColor = new MenuItem("Next color");
        nextColor.setOnAction(e -> icon.nextColor());
        return nextColor;
    }

    /**
     * Method to show specific data of a project.
     * @return context menu item.
     */
    private MenuItem showDetails() {
        var details = new MenuItem("Show details");
        details.setOnAction(action -> {
            MainController controller = getParentRoot().getMain();
            var graphController = controller.getGraphTabController();
            graphController.setGraphLabel(this.toString());
            Map<String, BigDecimal> newMap = new HashMap<>();
            juniors.forEach(task -> {
                    String taskName = task.getValue();
                    int seconds = 0;
                    for (Recording recording : task.getRecordings()) {
                        seconds += recording.getDurationInSec();
                    }
                    BigDecimal hours = BigDecimal.valueOf(seconds).divide(BigDecimal.valueOf(3600), 10, RoundingMode.HALF_UP);
                    newMap.put(taskName, hours);
                    graphController.updateGraph(newMap);
        });
    });
        return details;
    }

    /**
     * Creates a ContextMenu with the selected MenuItem-s depending on the archived state
     *
     * @return ContextMenu to be viewed with the right click on the ProjectTreeItem
     */
    @Override
    public ContextMenu getMenu() {

        MenuItem deleteProject = deleteItem("Delete project");
        MenuItem nextColor = nextColor();
        MenuItem projectDetails = showDetails();

        if (isArchived()) {
            MenuItem unArchive = unArchive();
            return new ContextMenu(deleteProject, unArchive, nextColor, projectDetails);
        }

        MenuItem addTask = createTask();
        MenuItem archive = archive();
        return new ContextMenu(addTask, deleteProject, archive, nextColor, projectDetails);
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
     * Method that organizes all the tasks and projects in a way stated in the TaskTreeItem and ProjectTreeItem compareTo method
     */
    @Override
    public void organizeView() {
        this.getJuniors().sort(TaskTreeItem::compareTo);
        this.getChildren().removeAll(this.getChildren());
        this.getChildren().addAll(juniors);
        this.getParentRoot().organizeView();
    }

    /**
     * Method for comparing project names with each other alphabetically
     *
     * @param o - comparable project
     * @return a negative integer, zero, or a positive integer as this project is less than, equal to, or greater than the specified project.
     */
    @Override
    public int compareTo(ProjectTreeItem o) {
        return this.getValue().compareTo(o.getValue());
    }
}
