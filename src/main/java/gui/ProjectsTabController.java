package gui;

import data.Data;
import data.DataHandler;
import gui.popups.CreateItemPopup;
import gui.treeItems.*;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import data.*;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

public class ProjectsTabController {

    private RootTreeItem projects;
    @FXML
    private TreeView<String> projectsTree;

    @FXML
    private void initialize() {

        // Test items for tree view
        // Root
        TreeItem<String> root = new TreeItem<>("Projects");

        // Visible roots
        projects = new RootTreeItem("Active projects");
        ArchivedRootTreeItem archived = new ArchivedRootTreeItem("Archived");
        root.getChildren().addAll(projects, archived);

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

        TaskTreeItem task3 = new TaskTreeItem("Demo Task 1");
        project2.getChildren().add(task3);
        Task newTask21 = new Task("Demo Task 1", LocalDateTime.now(), "Project Two");

        TaskTreeItem task4 = new TaskTreeItem("Demo Task 2");
        project2.getChildren().add(task4);
        Task newTask22 = new Task("Demo Task 2", LocalDateTime.now(), "Project Two");

        TaskTreeItem task5 = new TaskTreeItem("Demo Task 1");
        project3.getChildren().add(task5);
        Task newTask31 = new Task("Demo Task 1", LocalDateTime.now(), "Project Three");

        TaskTreeItem task6 = new TaskTreeItem("Demo Task 2");
        project3.getChildren().add(task6);
        Task newTask32 = new Task("Demo Task 2", LocalDateTime.now(), "Project Three");

        newProject1.addTask(newTask11);
        newProject1.addTask(newTask12);

        newProject2.addTask(newTask21);
        newProject2.addTask(newTask22);

        newProject3.addTask(newTask31);
        newProject3.addTask(newTask32);


        // Archived items

        ArchivedProjectTreeItem archivedProject1 = new ArchivedProjectTreeItem("ArchivedProject1");
        ArchivedProjectTreeItem archivedProject2 = new ArchivedProjectTreeItem("ArchivedProject2");
        archived.getChildren().addAll(archivedProject1, archivedProject2);

        ArchivedTaskTreeItem aTask1 = new ArchivedTaskTreeItem("ArchivedTask1");
        archivedProject1.getChildren().add(aTask1);
        ArchivedTaskTreeItem aTask2 = new ArchivedTaskTreeItem("ArchivedTask2");
        archivedProject1.getChildren().add(aTask2);
        ArchivedTaskTreeItem aTask3 = new ArchivedTaskTreeItem("ArchivedTask3");
        archivedProject2.getChildren().add(aTask3);
        ArchivedTaskTreeItem aTask4 = new ArchivedTaskTreeItem("ArchivedTask4");
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
//            System.out.println(activity.getValue());
            try {
                DataHandler.currentlyChosenTask = DataHandler.getTaskByName(activity.getValue());
                DataHandler.showCurrentlyChosen();
            } catch (Exception e) {
                System.out.println("This is not a task");
            }
        }
        // TODO return some value that can be used using selectItem() method
    }

    public void createProject(){
        CreateItemPopup createItemPopup = new CreateItemPopup();
        createItemPopup.popup(projects, "project");
    }

}
