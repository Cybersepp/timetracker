package logic.commands;

import data.FileAccess;
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
}
