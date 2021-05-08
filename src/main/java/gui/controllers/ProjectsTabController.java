package gui.controllers;

import data.FileAccess;
import data.Recording;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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


    @FXML
    private void initialize() {

        // Root
        TreeItem<String> root = new TreeItem<>("Projects");
        root.getChildren().addAll(projects, archived);

        //projects initialization
        initializeProjects();

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
                // GOOD

                for (Map.Entry<String, Object> entry : projectMap.entrySet()) {
                    String projectAttr = entry.getKey();
                    Object value = entry.getValue();
                    switch (projectAttr) {
                        case "isArchived":
                            if ((boolean) value) {
                                project.setArchived(archived);
                            }
                            break;
                        case "Tasks":
                            initializeTasks(project, (HashMap<String, Object>) value);
                            break;
                    }
                }
            });

        }
    }
    private void initializeTasks(ProjectTreeItem projectTreeItem, HashMap<String, Object> tasks) {
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
                        //TODO could be optimized if the json file would be changed a bit
                        var recordingInfo = (ArrayList<String>) value;
                        var recordings = new ArrayList<Recording>();
                        for (String info : recordingInfo) {
                            var split = info.split(", ");
                            var recording = new Recording(taskItem);
                            recording.setRecordStart(split[0]);
                            recording.setRecordEnd(split[1]);
                            recordings.add(recording);
                        }

                        taskItem.getRecordings().addAll(recordings);
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


    //------------- Graph views -------------------------//
    public void graphForLastWeek() throws ParseException {
        updateGraphByDays("Last 7 days", 7);
    }

    public void graphForLastMonth() throws ParseException {
        updateGraphByDays("Last 30 days", 30);
    }

    public void graphForLastYear() throws ParseException {
        updateGraphByDays("Last 365 days", 365);
    }

    public void graphForAllTime() throws ParseException {
        updateGraphLabel("All time");
        var historyTabController = mainController.getHistoryTabController();
        historyTabController.showByTime(historyTabController.getRecordLength());
    }

    public void updateGraphLabel(String graphLabel) {
        var graphTabController = mainController.getGraphTabController();
        graphTabController.setGraphLabel(graphLabel);
    }

    public void updateGraphByDays(String graphLabel, int days) throws ParseException {
        updateGraphLabel(graphLabel);
        var historyTabController = mainController.getHistoryTabController();
        historyTabController.showByTime(days);
    }

}
