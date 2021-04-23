package logic;

import logic.treeItems.ProjectTreeItem;
import logic.treeItems.RootTreeItem;
import logic.treeItems.TaskTreeItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectTreeItemTest {

    @Test
    void theRootOfProjectChangedWhileArchiving() {
        var activeRoot = new RootTreeItem("Active");
        var archivedRoot = new RootTreeItem("Archived", true);
        var testProject = new ProjectTreeItem("Test");
        activeRoot.addJunior(testProject);
        testProject.setArchived(archivedRoot);
        assertTrue(archivedRoot.getJuniors().contains(testProject) && !activeRoot.getJuniors().contains(testProject));
    }

    @Test
    void theArchivedStateOfProjectChangedWhileArchiving() {
        var activeRoot = new RootTreeItem("Active");
        var archivedRoot = new RootTreeItem("Archived", true);
        var testProject1 = new ProjectTreeItem("Test1");
        var testProject2 = new ProjectTreeItem("Test2");

        activeRoot.addJunior(testProject1);
        activeRoot.addJunior(testProject2);

        testProject1.setArchived(archivedRoot);
        testProject2.setArchived(archivedRoot);
        testProject2.setArchived(activeRoot);

        assertTrue(testProject1.isArchived() && !testProject2.isArchived());
    }

    @Test
    void TaskInListAfterBeingAdded() {
        var project = new ProjectTreeItem("Project");
        var task = new TaskTreeItem("Task");
        project.addJunior(task);
        assertTrue(project.getJuniors().contains(task) && project.getChildren().contains(task));
    }

    @Test
    void noWrongProjectInListAfterBeingAdded() {
        var project = new ProjectTreeItem("Project");
        var task = new TaskTreeItem("Task");
        project.addJunior(task);
        assertFalse(project.getJuniors().contains(new TaskTreeItem("Task2")));
    }

    @Test
    void noProjectInListAfterBeingRemoved() {
        var project = new ProjectTreeItem("Project");
        var task = new TaskTreeItem("Task");
        project.addJunior(task);
        project.removeJunior(task);
        assertFalse(project.getJuniors().contains(task) && project.getChildren().contains(task));
    }

    @Test
    void noProjectInListWhileCreating() {
        var project = new RootTreeItem("Project");
        assertTrue(project.getJuniors().isEmpty() && project.getChildren().isEmpty());
    }
}
