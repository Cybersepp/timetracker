package gui;

import data.Data;
import data.DataHandler;
import gui.popups.CreateItemPopup;
import gui.treeItems.*;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

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
            DataHandler.currentlyChosenTask = DataHandler.getTaskByName(activity.getValue());
            DataHandler.showCurrentlyChosen();
        }
        // TODO return some value that can be used using selectItem() method
    }

    public void createProject(){
        CreateItemPopup createItemPopup = new CreateItemPopup();
        createItemPopup.popup(projects, "project");
    }

}
