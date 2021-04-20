package gui.controllers;

import data.FileAccess;
import gui.popups.CreateItemPopup;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import logic.Treeitems.*;
import logic.commands.DeleteProjectCommand;
import logic.commands.DeleteTaskCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectsTabController {

    private static final RootTreeItem projects = new RootTreeItem("Active projects");
    private static final RootTreeItem archived = new RootTreeItem("Archived", true);

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
        root.getChildren().addAll(projects, archived);

        Map<String, Map<String, List<String>>> dataMap = FileAccess.getProjectData();

        if (dataMap != null) {

            dataMap.forEach((name, taskMap) -> {
                ProjectTreeItem project = new ProjectTreeItem(name);

                taskMap.forEach((taskName, records) -> {
                    TaskTreeItem task = new TaskTreeItem(taskName);
                    task.getRecords().addAll(records);
                    project.addJunior(task);
                });

                projects.addJunior(project);
            });
        }

        // -------- Demo items for tree view ----------------

        var archivedProject1 = new ProjectTreeItem("ArchivedProject1", new ArrayList<>(), true);
        var archivedProject2 = new ProjectTreeItem("ArchivedProject2", new ArrayList<>(), true);
        archived.addJunior(archivedProject1);
        archived.addJunior(archivedProject2);

        var aTask1 = new TaskTreeItem("ArchivedTask1", true, false, new ArrayList<>());
        archivedProject1.addJunior(aTask1);
        var aTask2 = new TaskTreeItem("ArchivedTask2", true, false, new ArrayList<>());
        archivedProject1.addJunior(aTask2);
        var aTask3 = new TaskTreeItem("ArchivedTask3", true, false, new ArrayList<>());
        archivedProject2.addJunior(aTask3);
        var aTask4 = new TaskTreeItem("ArchivedTask4", true, false, new ArrayList<>());
        archivedProject2.addJunior(aTask4);

        // ------------------ Demo items end for tree view -----------------------

        // tree configuration
        projectsTree.setShowRoot(false);
        projectsTree.setRoot(root);
        projectsTree.setCellFactory(p -> new TreeCellImplication());

        // "DEL" function on projects and tasks
        projectsTree.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                if (selectItem().getClass() == ProjectTreeItem.class) {
                    new DeleteProjectCommand((ProjectTreeItem) selectItem()).commandControl();
                }
                else if (selectItem().getClass() == TaskTreeItem.class) {
                    new DeleteTaskCommand((TaskTreeItem) selectItem()).commandControl();
                }
            }
        });
    }

    /**
     * Method for selecting projects and project tasks and sending out value.
     * @return selected treeItem on treeView
     */
    public AbstractTreeItem selectItem() {
        return (AbstractTreeItem) projectsTree.getSelectionModel().getSelectedItem();
    }



    /**
     * Create project button functionality
     */
    public void createProject(){
        new CreateItemPopup(projects, "project").popup();
    }

    @Override
    public String toString() {
        return "project";
    }

}
