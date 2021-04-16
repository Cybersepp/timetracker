package logic.commands;

import data.FileAccess;
import logic.Treeitems.ProjectTreeItem;
import logic.Treeitems.TaskTreeItem;

public class DeleteTaskCommand implements Command{

    private final TaskTreeItem task;

    public DeleteTaskCommand(TaskTreeItem task) {
        this.task = task;
    }

    @Override
    public void command() {

        var parent = (ProjectTreeItem) task.getParent();
        parent.removeJunior(task);
        FileAccess.saveData();
    }
}
