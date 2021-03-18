package gui;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ProjectsTabController {

    @FXML
    private TreeView<String> projectsTree;

    @FXML
    private void initialize() {

        // Test items for tree view
        // Root
        TreeItem<String> projects = new TreeItem<>("Projects");

        // Project items
        TreeItem<String> project1 = createProject(projects, "Project1");
        TreeItem<String> project2 = createProject(projects, "Project1");
        TreeItem<String> project3 = createProject(projects, "Project1");
        TreeItem<String> project4 = createProject(projects, "Project1");

        createTask(project1, "task2");
        createTask(project1, "task1");
        createTask(project2, "task3");
        createTask(project2, "task4");
        createTask(project3, "task5");
        createTask(project3, "task6");
        createTask(project4, "task7");
        createTask(project4, "task8");

        // Archived items
        TreeItem<String> archived = createItem(projects, "Archived");
        // TODO archived should not be editable nor deletable branch

        TreeItem<String> archivedProject1 = createProject(archived, "ArchivedProject1");
        TreeItem<String> archivedProject2 = createProject(archived, "ArchivedProject2");

        createTask(archivedProject1, "ArchivedTask1");
        createTask(archivedProject1, "ArchivedTask2");
        createTask(archivedProject2, "ArchivedTask3");
        createTask(archivedProject2, "ArchivedTask4");

        // tree configuration
        projectsTree.setShowRoot(false);
        projectsTree.setRoot(projects);
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
    }

    /**
     * Method for creating a new project branch.
     * @param root the branch of the project which the item is situated on.
     * @param projectName the name of the created item.
     */
    public TreeItem<String> createProject(TreeItem<String> root, String projectName){
        TreeItem<String> project = createItem(root, projectName);
        // TODO add configuration specific to all projects
        return project;
    }

    /**
     * Method for creating a new task in a specific project branch.
     * @param project the branch of the project which the task is situated on.
     * @param taskName the name of displayable task name.
     */
    public TreeItem<String> createTask(TreeItem<String> project, String taskName){
        TreeItem<String> task = createItem(project, taskName);
        // TODO add configuration specific to all tasks
        return task;
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
}
