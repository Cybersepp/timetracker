package gui;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ProjectsTabController {

    @FXML
    private TreeView<String> projectsTree;
    TreeItem<String> projects = null;

    @FXML
    private void initialize() {

        // Test items for tree view
        // Root
        TreeItem<String> root = new TreeItem<>("Projects");

        // Visible roots
        RootTreeItem projects = new RootTreeItem("Active projects");
        RootTreeItem archived = new RootTreeItem("Archived");
        root.getChildren().addAll(projects, archived);
        // TODO visible roots should not be editable nor deletable branches

        // Project items
        ProjectTreeItem project1 = new ProjectTreeItem("Project1");
        projects.getChildren().add(project1);
        ProjectTreeItem project2 = new ProjectTreeItem("Project2");
        projects.getChildren().add(project2);
        ProjectTreeItem project3 = new ProjectTreeItem("Project3");
        projects.getChildren().add(project3);
        ProjectTreeItem project4 = new ProjectTreeItem("Project4");
        projects.getChildren().add(project4);

        TaskTreeItem task1 = new TaskTreeItem("task1");
        project1.getChildren().add(task1);
        TaskTreeItem task2 = new TaskTreeItem("task2");
        project1.getChildren().add(task2);
        TaskTreeItem task3 = new TaskTreeItem("task3");
        project2.getChildren().add(task3);
        TaskTreeItem task4 = new TaskTreeItem("task4");
        project2.getChildren().add(task4);
        TaskTreeItem task5 = new TaskTreeItem("task5");
        project3.getChildren().add(task5);
        TaskTreeItem task6 = new TaskTreeItem("task6");
        project3.getChildren().add(task6);
        TaskTreeItem task7 = new TaskTreeItem("task7");
        project4.getChildren().add(task7);
        TaskTreeItem task8 = new TaskTreeItem("task8");
        project4.getChildren().add(task8);

        // Archived items

        ProjectTreeItem archivedProject1 = new ProjectTreeItem("ArchivedProject1");
        ProjectTreeItem archivedProject2 = new ProjectTreeItem("ArchivedProject2");
        archived.getChildren().addAll(archivedProject1, archivedProject2);

        TaskTreeItem aTask1 = new TaskTreeItem("ArchivedTask1");
        archivedProject1.getChildren().add(aTask1);
        TaskTreeItem aTask2 = new TaskTreeItem("ArchivedTask2");
        archivedProject1.getChildren().add(aTask2);
        TaskTreeItem aTask3 = new TaskTreeItem("ArchivedTask3");
        archivedProject2.getChildren().add(aTask3);
        TaskTreeItem aTask4 = new TaskTreeItem("ArchivedTask4");
        archivedProject2.getChildren().add(aTask4);


        // tree configuration
        projectsTree.setShowRoot(false);
        projectsTree.setRoot(root);
        projectsTree.setCellFactory(p -> new TreeCellImplication());
        // Test items end for tree view

    }

    /**
     * Method for selecting projects and project tasks and sending out value.
     */
    public void selectItem() {
        TreeItem<String> activity = projectsTree.getSelectionModel().getSelectedItem();
        if (activity != null) {
            System.out.println(activity.getValue());
        }
        // TODO return some value that can be used using selectItem() method
    }

    /**
     * Method for creating a new tree item in the tab and making it a child of an existing item.
     * @param root the branch of the project which the item is situated on.
     * @param itemName the name of the created item.
     */
    private TreeItem<String> createItem(TreeItem<String> root, String itemName){
        TreeItem<String> item = new TreeItem<>(itemName);
        root.getChildren().add(item);
        return item;
        // TODO add configuration specific to all projects
        // TODO add configuration specific to all tasks
    }

    /**
     * Method for archiving projects.
     * @param root the root of the project.
     * @param project the instance of a project that is about to be archived.
     * @param archive the archive branch.
     */
    public void archiveProject(TreeItem<String> root, TreeItem<String> project, TreeItem<String> archive){
        root.getChildren().remove(project);
        archive.getChildren().add(project);
        // TODO archived projects are unable to start recordings
        // TODO check if the project is a project and not a task?
    }

    /**
     * Method for marking a task as done and crossing it out.
     * @param project the branch of the project which the task is situated on.
     * @param task the instance of a task that is about to be crossed out / be marked as done.
     */
    public void markTaskAsDone(TreeItem<String> project, TreeItem<String> task){
        // TODO mark task as done and cross it out
        // TODO done tasks should not be able to add any recordings
        // TODO crossed out tasks should be at the end of the project's task list
    }

    /**
     * Method for removing items from the tree
     * @param root the root of the removable item
     * @param item the item that is about to be removed from the root
     */
    public void deleteItem(TreeItem<String> root, TreeItem<String> item) {
        root.getChildren().remove(item);
        // TODO should send out a warning message if there are any recordings associated with the item
        // TODO should also delete all history that is associated with this item
    }


    public void changeItemName() {
        // TODO find a way to change project / task name and keep it's data intact (tasks, time spent)
    }

    //Richard hello new method here
    public TreeItem<String> getMainTree() {
        return projects;
    }
}
