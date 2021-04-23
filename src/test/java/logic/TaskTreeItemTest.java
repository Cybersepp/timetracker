package logic;

import logic.treeItems.ProjectTreeItem;
import logic.treeItems.RootTreeItem;
import logic.treeItems.TaskTreeItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTreeItemTest {

    @Test
    void archivedStateOfTaskChangedAfterArchiving() {
        var activeRoot = new RootTreeItem("Active");
        var archivedRoot = new RootTreeItem("Archived", true);
        var testProject1 = new ProjectTreeItem("Test1");
        var testProject2 = new ProjectTreeItem("Test2");
        var task1 = new TaskTreeItem("Task1");
        var task2 = new TaskTreeItem("Task2");
        var task3 = new TaskTreeItem("Task3");
        var task4 = new TaskTreeItem("Task4");

        activeRoot.addJunior(testProject1);
        activeRoot.addJunior(testProject2);
        testProject1.addJunior(task1);
        testProject1.addJunior(task2);
        testProject2.addJunior(task3);
        testProject2.addJunior(task4);

        testProject1.setArchived(archivedRoot);
        testProject2.setArchived(archivedRoot);
        testProject2.setArchived(activeRoot);

        assertTrue(task1.isArchived() && task2.isArchived() && !task3.isArchived() && !task4.isArchived());
    }

}
