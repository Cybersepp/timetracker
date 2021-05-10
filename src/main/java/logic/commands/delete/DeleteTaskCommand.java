package logic.commands.delete;

import data.FileAccess;
import gui.popups.notification.WarningPopup;
import logic.commands.Command;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.TaskTreeItem;

public class DeleteTaskCommand implements Command {

    private final TaskTreeItem task;

    public DeleteTaskCommand(TaskTreeItem task) {
        this.task = task;
    }

    /**
     * Deletes a task
     */
    @Override
    public void command() {
        var parent = (ProjectTreeItem) task.getParent();
        parent.removeJunior(task);
        FileAccess.saveData();
    }

    /**
     * Calls a popup with the choice to delete a task
     */
    @Override
    public void commandControl() {
        if (task.getRecordings().isEmpty()) {
            this.command();
        }
        else {
            var warningPopup = new WarningPopup("Are you sure you want to delete this task and all of its records?", this);
            warningPopup.popup();
        }
    }
}
