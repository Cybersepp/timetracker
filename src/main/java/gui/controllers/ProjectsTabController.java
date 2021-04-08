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
        projects.addJunior(project1);

        ProjectTreeItem project2 = new ProjectTreeItem("Project Two");
        projects.addJunior(project2);

        ProjectTreeItem project3 = new ProjectTreeItem("Project Three");
        projects.addJunior(project3);

        TaskTreeItem task1 = new TaskTreeItem("Demo Task 1");
        project1.addJunior(task1);

        TaskTreeItem task2 = new TaskTreeItem("Demo Task 2");
        project1.addJunior(task2);

        TaskTreeItem task3 = new TaskTreeItem("Demo Task 3");
        project2.addJunior(task3);

        TaskTreeItem task4 = new TaskTreeItem("Demo Task 4");
        project2.addJunior(task4);

        TaskTreeItem task5 = new TaskTreeItem("Demo Task 5");
        project3.addJunior(task5);

        TaskTreeItem task6 = new TaskTreeItem("Demo Task 6");
        project3.addJunior(task6);

        // Archived items

        ProjectTreeItem archivedProject1 = new ProjectTreeItem("ArchivedProject1");
        ProjectTreeItem archivedProject2 = new ProjectTreeItem("ArchivedProject2");
        archivedProject1.setArchived(true);
        archivedProject2.setArchived(true);
        archived.addJunior(archivedProject1);
        archived.addJunior(archivedProject2);

        TaskTreeItem aTask1 = new TaskTreeItem("ArchivedTask1");
        archivedProject1.addJunior(aTask1);
        TaskTreeItem aTask2 = new TaskTreeItem("ArchivedTask2");
        archivedProject1.addJunior(aTask2);
        TaskTreeItem aTask3 = new TaskTreeItem("ArchivedTask3");
        archivedProject2.addJunior(aTask3);
        TaskTreeItem aTask4 = new TaskTreeItem("ArchivedTask4");
        archivedProject2.addJunior(aTask4);
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
    public AbstractTreeItem selectItem() {
        AbstractTreeItem activity = (AbstractTreeItem) projectsTree.getSelectionModel().getSelectedItem();
        if (activity != null) {
            System.out.println(activity.getValue());
        }
        return activity;
    }

    public void createProject(){
        CreateItemPopup createItemPopup = new CreateItemPopup(projects, "project");
        createItemPopup.popup();
    }

}
