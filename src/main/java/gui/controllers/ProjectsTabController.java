package gui.controllers;

import gui.popups.action.treeItemAction.CreateItemPopup;
import gui.popups.action.treeItemAction.CreateTaskButtonPopup;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import logic.services.ProjectTabService;
import logic.treeItems.AbstractTreeItem;
import logic.treeItems.RootTreeItem;

public class ProjectsTabController {

    private static final RootTreeItem activeRoot = new RootTreeItem("Active projects");
    private static final RootTreeItem archivedRoot = new RootTreeItem("Archived", true);

    public static RootTreeItem getActiveRoot() {
        return activeRoot;
    }

    public static RootTreeItem getArchivedRoot() {
        return archivedRoot;
    }

    private ProjectTabService projectTabService;

    @FXML
    private TreeView<String> projectsTree;

    private MainController mainController;


    @FXML
    private void initialize() {
        projectTabService = new ProjectTabService(activeRoot, archivedRoot, projectsTree);
        projectTabService.initializeData(new TreeItem<>("Projects"));
    }

    /**
     * Method for selecting projects and project tasks and sending out value.
     * @return selected treeItem on treeView
     */
    public AbstractTreeItem selectItem() {
        return projectTabService.selectItem();
    }

    /**
     * Create project button functionality
     */
    public void createProject() {
        new CreateItemPopup(activeRoot, "project").popup();
    }

    /**
     * Create task button functionality
     */
    public void createTask() {
        new CreateTaskButtonPopup().popup();
    }

    public void showInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Projects' tab information");
        alert.setHeaderText(null);
        alert.setContentText("Interact with items using right click.\n" +
                            "Move around the tree using your mouse or the arrow keys.\n\n" +
                            "'enter' - changes project / task name.\n" +
                            "'del' - deletes selected project / task.");
        alert.showAndWait();
    }

    public void init(MainController main) {
        mainController = main;
        activeRoot.addMain(main);
    }

    //------------- Graph views -------------------------//
    public void graphForLastWeek(){
        updateGraphByDays("Last 7 days", 7);
    }

    public void graphForLastMonth(){
        updateGraphByDays("Last 30 days", 30);
    }

    public void graphForLastYear(){
        updateGraphByDays("Last 365 days", 365);
    }

    public void graphForAllTime(){
        updateGraphByDays("All time", Integer.MAX_VALUE);
    }

    public void updateGraphLabel(String graphLabel) {
        var graphTabController = mainController.getGraphTabController();
        graphTabController.setGraphLabel(graphLabel);
    }
    public void updateGraphByDays(String graphLabel, int days){
        updateGraphLabel(graphLabel);
        var historyTabController = mainController.getHistoryTabController();
        historyTabController.showByTime(days, mainController.getGraphTabController());
    }

}
