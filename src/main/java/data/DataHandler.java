package data;

import logic.treeItems.TaskTreeItem;

public class DataHandler {

    private TaskTreeItem currentlyChosenTask;

    public TaskTreeItem getCurrentlyChosenTask() {
        return currentlyChosenTask;
    }

    public void setCurrentlyChosenTask(TaskTreeItem currentlyChosenTask) {
        this.currentlyChosenTask = currentlyChosenTask;
    }
}
