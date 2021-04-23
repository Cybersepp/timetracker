package logic.commands;

import data.FileAccess;
import gui.popups.WarningPopup;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.TaskTreeItem;

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

    @Override
    public void commandControl() {
        if (task.getRecords().isEmpty()) {
            this.command();
        }
        else {
            var warningPopup = new WarningPopup("Are you sure you want to delete this task and all of its records?", this);
            warningPopup.popup();
        }
    }
}
