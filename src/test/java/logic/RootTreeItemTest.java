package logic;

import logic.treeItems.ProjectTreeItem;
import logic.treeItems.RootTreeItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RootTreeItemTest {

    @Test
    void projectIsInListAfterBeingAdded() {
        var root = new RootTreeItem("Root");
        var test = new ProjectTreeItem("Test");
        root.addJunior(test);
        assertTrue(root.getJuniors().contains(test) && root.getChildren().contains(test));
    }

    @Test
    void noWrongProjectInListAfterBeingAdded() {
        var root = new RootTreeItem("Root");
        var test = new ProjectTreeItem("Test");
        root.addJunior(test);
        assertFalse(root.getJuniors().contains(new ProjectTreeItem("Test2")));
    }

    @Test
    void noProjectInListAfterBeingRemoved() {
        var root = new RootTreeItem("Root");
        var test = new ProjectTreeItem("Test");
        root.addJunior(test);
        root.removeJunior(test);
        assertFalse(root.getJuniors().contains(test) && root.getChildren().contains(test));
    }

    @Test
    void noProjectInListWhileCreating() {
        var root = new RootTreeItem("Root");
        assertTrue(root.getJuniors().isEmpty() && root.getChildren().isEmpty());
    }

}
