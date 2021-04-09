package data;

import gui.controllers.ProjectsTabController;
import logic.Treeitems.TaskTreeItem;

public class DataHandler {

    private TaskTreeItem currentlyChosenTask;

    public TaskTreeItem getCurrentlyChosenTask() {
        return currentlyChosenTask;
    }

    public void setCurrentlyChosenTask(TaskTreeItem currentlyChosenTask) {
        this.currentlyChosenTask = currentlyChosenTask;
    }

    // TODO Nikita read this below

    /*
        So i am going to write a little explanation how to get everything you had here before

        * First of all if you tap on a project/task/root you can access it with ProjectsTabController SelectItem() method
        * List of Projects: RootTreeItem.getJuniors() (there are two root items "archived" and "active projects")
        * List of tasks for a certain project: ProjectTreeItem.getJuniors()
        * Get a name of a task or project or root: AbstractTreeItem.getValue()

        Then I don't understand why would you need to find some task or project by name, but if you need to use it somewhere
        then you can just get a list of projects or list of tasks from a certain project in the way I described before
        and check if that exists there as you can get a projects or tasks name in the list by item.getValue()
        And same goes for the checkName operation you had here

     */

    // not sure if you actually need this method aswell
    public static void showProjects() {
        System.out.println("Active Projects:");
        ProjectsTabController.getProjects().getJuniors().forEach(project -> System.out.println(project.getValue()));

        System.out.println("Archived Projects:");
        ProjectsTabController.getArchived().getJuniors().forEach(project -> System.out.println(project.getValue()));
    }
}
