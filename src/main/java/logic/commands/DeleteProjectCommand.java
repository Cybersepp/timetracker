package logic.commands;

import data.FileAccess;
import gui.popups.WarningPopup;
import logic.Treeitems.ProjectTreeItem;
import logic.Treeitems.RootTreeItem;

public class DeleteProjectCommand implements Command{

    private final ProjectTreeItem project;

    public DeleteProjectCommand(ProjectTreeItem project) {
        this.project = project;
    }

    @Override
    public void command() {
        var parent = (RootTreeItem) project.getParent();
        parent.removeJunior(project);
        FileAccess.saveData();
    }

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
