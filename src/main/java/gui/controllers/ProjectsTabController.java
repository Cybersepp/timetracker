package gui.controllers;

import data.DataHandler;
import gui.popups.CreateItemPopup;
import logic.treeItems.*;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import data.*;

import java.time.LocalDateTime;

public class ProjectsTabController {

    private static final RootTreeItem projects = new RootTreeItem("Active projects");
    private static final RootTreeItem archived = new RootTreeItem("Archived");

    public static RootTreeItem getProjects() {
        return projects;
    }

    public static RootTreeItem getArchived() {
        return archived;
    }

    @FXML
    private TreeView<String> projectsTree;

    @FXML
    private void initialize() {

        // Root
        TreeItem<String> root = new TreeItem<>("Projects");
        archived.setArchived(true);
        root.getChildren().addAll(projects, archived);

        // -------- Demo items for tree view ----------------

        // Project items
        ProjectTreeItem project1 = new ProjectTreeItem("Project One");
        projects.getChildren().add(project1);
        Project newProject1 = new Project("Project One", LocalDateTime.now());

        ProjectTreeItem project2 = new ProjectTreeItem("Project Two");
        projects.getChildren().add(project2);
        Project newProject2 = new Project("Project Two", LocalDateTime.now());

        ProjectTreeItem project3 = new ProjectTreeItem("Project Three");
        projects.getChildren().add(project3);
        Project newProject3 = new Project("Project Three", LocalDateTime.now());

        DataHandler.addProject(newProject1);
        DataHandler.addProject(newProject2);
        DataHandler.addProject(newProject3);

        TaskTreeItem task1 = new TaskTreeItem("Demo Task 1");
        project1.getChildren().add(task1);
        Task newTask11 = new Task("Demo Task 1", LocalDateTime.now(), "Project One");

        TaskTreeItem task2 = new TaskTreeItem("Demo Task 2");
        project1.getChildren().add(task2);
        Task newTask12 = new Task("Demo Task 2", LocalDateTime.now(), "Project One");

        TaskTreeItem task3 = new TaskTreeItem("Demo Task 3");
        project2.getChildren().add(task3);
        Task newTask21 = new Task("Demo Task 3", LocalDateTime.now(), "Project Two");

        TaskTreeItem task4 = new TaskTreeItem("Demo Task 4");
        project2.getChildren().add(task4);
        Task newTask22 = new Task("Demo Task 4", LocalDateTime.now(), "Project Two");

        TaskTreeItem task5 = new TaskTreeItem("Demo Task 5");
        project3.getChildren().add(task5);
        Task newTask31 = new Task("Demo Task 5", LocalDateTime.now(), "Project Three");

        TaskTreeItem task6 = new TaskTreeItem("Demo Task 6");
        project3.getChildren().add(task6);
        Task newTask32 = new Task("Demo Task 6", LocalDateTime.now(), "Project Three");

        newProject1.addTask(newTask11);
        newProject1.addTask(newTask12);

        newProject2.addTask(newTask21);
        newProject2.addTask(newTask22);

        newProject3.addTask(newTask31);
        newProject3.addTask(newTask32);


        // Archived items

        ProjectTreeItem archivedProject1 = new ProjectTreeItem("ArchivedProject1");
        ProjectTreeItem archivedProject2 = new ProjectTreeItem("ArchivedProject2");
        archivedProject1.setArchived(true);
        archivedProject2.setArchived(true);
        archived.getChildren().addAll(archivedProject1, archivedProject2);

        TaskTreeItem aTask1 = new TaskTreeItem("ArchivedTask1");
        archivedProject1.getChildren().add(aTask1);
        TaskTreeItem aTask2 = new TaskTreeItem("ArchivedTask2");
        archivedProject1.getChildren().add(aTask2);
        TaskTreeItem aTask3 = new TaskTreeItem("ArchivedTask3");
        archivedProject2.getChildren().add(aTask3);
        TaskTreeItem aTask4 = new TaskTreeItem("ArchivedTask4");
        archivedProject2.getChildren().add(aTask4);
        aTask1.setArchived(true);
        aTask2.setArchived(true);
        aTask3.setArchived(true);
        aTask4.setArchived(true);

        // ------------------ Demo items end for tree view -----------------------

        // tree configuration
        projectsTree.setShowRoot(false);
        projectsTree.setRoot(root);
        projectsTree.setCellFactory(p -> new TreeCellImplication());
    }

    /**
     * Method for selecting projects and project tasks and sending out value.
     * @return selected treeItem on treeView
     */
    public TreeItem<String> selectItem() {
        TreeItem<String> activity = projectsTree.getSelectionModel().getSelectedItem();
        if (activity != null) {
            System.out.println(activity.getValue());
            try {
                DataHandler.currentlyChosenTask = DataHandler.getTaskByName(activity.getValue());
                DataHandler.showCurrentlyChosen();
            } catch (Exception e) {
                System.out.println("This is not a task");
            }
        }
        return activity;
    }

    public void createProject(){
        CreateItemPopup createItemPopup = new CreateItemPopup(projects, "project");
        createItemPopup.popup();
    }

}
