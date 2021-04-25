package gui.controllers;

import data.FileAccess;
import gui.popups.action.CreateItemPopup;
import gui.popups.action.CreateTaskButtonPopup;
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
import java.util.HashMap;
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

        // -------- Demo items for tree view ----------------

        initializeProjects();

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

    //TODO remake this f-word thing later to use ProjectTreeItem and TaskTreeItem as a Data Class
    private void initializeProjects() {
        Map<String, Map<String, Object>> dataMap = FileAccess.getProjectData();

        if (dataMap != null) {

            dataMap.forEach((name, projectMap) -> {
                ProjectTreeItem project = new ProjectTreeItem(name);
                projects.addJunior(project);

                projectMap.forEach((projectAttr, value) -> {
                    switch (projectAttr) {
                        case "isArchived":
                            if ((boolean) value) {
                                project.setArchived(archived);
                            }
                            break;
                        case "Tasks":
                            System.out.println("Start task init");
                            initializeTasks(project, (HashMap<String, Object>) value);
                        default:
                    }
                });
            });

        }
    }
    private void initializeTasks(ProjectTreeItem projectTreeItem, HashMap<String, Object> tasks) {
        System.out.println("Here we are");
        System.out.println(tasks);
        tasks.forEach((task, taskInfo) -> {
            TaskTreeItem taskItem = new TaskTreeItem(task);
            HashMap<String, Object> taskHash = (HashMap<String, Object>) taskInfo;

            taskHash.forEach((taskAttr, value) -> {
                switch (taskAttr) {
                    case "isDone":
                        if ((boolean) value) {
                            taskItem.setDone(true);
                            break;
                        }
                        taskItem.setDone(false);
                        break;
                    case "Records":
                        taskItem.getRecords().addAll((ArrayList<String>) value);
                        break;
                    default:
                }
            });
            projectTreeItem.addJunior(taskItem);

        });
    }

    /**
     * Create project button functionality
     */
    public void createProject() {
        new CreateItemPopup(projects, "project").popup();
    }

    /**
     * Create task button functionality
     */
    public void createTask() {
        new CreateTaskButtonPopup().popup();
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
