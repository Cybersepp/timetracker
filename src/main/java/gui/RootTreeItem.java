package gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class RootTreeItem extends AbstractTreeItem{

    public RootTreeItem(String name) {
        this.setValue(name);
    }

    @Override
    public ContextMenu getMenu() {
        MenuItem addProject = new MenuItem("add project");
        addProject.setOnAction(new EventHandler() {
            public void handle(Event t) {
                ProjectTreeItem newProject = new ProjectTreeItem("Return CCCP to former glory");
                getChildren().add(newProject);
            }
        });
        return new ContextMenu(addProject);
    }
}
