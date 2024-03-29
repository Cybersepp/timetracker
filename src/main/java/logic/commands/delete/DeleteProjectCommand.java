package logic.commands.delete;

import data.FileAccess;
import gui.popups.notification.WarningPopup;
import logic.commands.Command;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.RootTreeItem;

public class DeleteProjectCommand implements Command {

    private final ProjectTreeItem project;

    public DeleteProjectCommand(ProjectTreeItem project) {
        this.project = project;
    }

    /**
     * Deletes a project
     */
    @Override
    public void command() {
        var parent = (RootTreeItem) project.getParent();
        parent.removeJunior(project);
        FileAccess.saveData();
    }

    /**
     * Calls a popup with the choice to delete a project
     */
    @Override
    public void commandControl() {
        if (project.getChildren().isEmpty()) {
            this.command();
        }
        else {
            var warningPopup = new WarningPopup("Are you sure you want to delete this project and all of its data?", this);
            warningPopup.popup();
        }
    }
}
