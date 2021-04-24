package gui.controllers;

import data.FileAccess;
import gui.popups.action.CreateItemPopup;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import logic.commands.delete.DeleteProjectCommand;
import logic.commands.delete.DeleteTaskCommand;
import logic.treeItems.*;
import logic.treeItems.TreeCellFactory;

import java.text.ParseException;
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

    // ----------- GRAPH TIME OPTIONS ------------------
    @FXML
    private MenuItem lastWeek;

    @FXML
    private MenuItem lastMonth;

    @FXML
    private MenuItem lastYear;

    @FXML
    private MenuItem allTime;

    private MainController mainController;

    private HistoryTabController historyTabController;

    private GraphTabController graphTabController;


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
        projectsTree.setCellFactory(p -> new TreeCellFactory());

        projects.setExpanded(true);
        archived.setExpanded(false);

        // "DEL" function on projects and tasks
        projectsTree.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                if (selectItem().getClass() == ProjectTreeItem.class) {
                    new DeleteProjectCommand((ProjectTreeItem) selectItem()).commandControl();
                } else if (selectItem().getClass() == TaskTreeItem.class) {
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
    public void createProject() {
        new CreateItemPopup(projects, "project").popup();
    }

    @Override
    public String toString() {
        return "project";
    }

    public void init(MainController main) {
        mainController = main;
    }

    //TODO DRY principle a bit lost, gotta optimize
    public void graphForLastWeek() throws ParseException {
        historyTabController = mainController.getHistoryTabController();
        historyTabController.showByTime(7);
        graphTabController = mainController.getGraphTabController();
        graphTabController.setGraphLabel("Last 7 days");
    }

    public void graphForLastMonth() throws ParseException {
        historyTabController = mainController.getHistoryTabController();
        historyTabController.showByTime(30);
        graphTabController = mainController.getGraphTabController();
        graphTabController.setGraphLabel("Last 30 days");
    }

    public void graphForLastYear() throws ParseException {
        historyTabController = mainController.getHistoryTabController();
        historyTabController.showByTime(365);
        graphTabController = mainController.getGraphTabController();
        graphTabController.setGraphLabel("Last 365 days");
    }

    public void graphForAllTime() {
        graphTabController = mainController.getGraphTabController();
        graphTabController.initUpdateGraph();
        graphTabController = mainController.getGraphTabController();
        graphTabController.setGraphLabel("All time");

    }
}
