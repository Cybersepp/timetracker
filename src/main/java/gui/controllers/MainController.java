package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import logic.services.MainTabService;

import java.io.IOException;
import java.text.ParseException;

/**
 * MainController class is made for functionality of the UI elements (Not MVC sorry).
 */
public class MainController {


    //TODO bad practice "hiding" tabs from the user by simply disabling window/button and setting opacity to 0, need
    // to change

    // ---- CONTROLLER INSTANCES ----

    @FXML
    private ProjectsTabController projectsTabController;

    @FXML
    private GraphTabController graphTabController;

    @FXML
    private HistoryTabController historyTabController;

    @FXML
    private AutotrackTabController autotrackTabController;


    // ---- WINDOWS ----

    @FXML
    private AnchorPane graphTab;

    private AnchorPane historyTab;

    private AnchorPane autotrackTab;

    private AnchorPane currentTab;

    @FXML
    private AnchorPane rightWindow;


    // ---- UI ELEMENTS ----

    @FXML
    private Button historyAndGraphButton;

    @FXML
    private Button recordButton;

    @FXML
    private Label timerId;

    @FXML
    private Rectangle timeLine;

    @FXML
    private Button autotrackButton;

    // ---- DAO ----

    private MainTabService mainTabService;

    public MainTabService getMainTabService() {
        return mainTabService;
    }

    @FXML
    private void initialize() throws IOException {
        mainTabService = new MainTabService(recordButton, timeLine, timerId);
        projectsTabController.init(this);
        injectHistoryTab();
        injectAutotrackTab();
        currentTab = graphTab;

    }
    // ---- GETTERS FOR CONTROLLERS ----
    // If history tab controller wants to communicate with graph tab controller,
    // it gets graph tab controller from the main controller.
    // As far as I'm concerned, there is no better way for subcontroller communication available in JavaFX.

    public HistoryTabController getHistoryTabController() {
        return historyTabController;
    }

    public ProjectsTabController getProjectsTabController() {
        return projectsTabController;
    }

    public GraphTabController getGraphTabController() {
        return graphTabController;
    }
    // --------------------------------------------------------------

    /**
     * When user ends recording, updateRecordButton method updates the graph, stops the timer and its animation,
     * and saves newly created entry to the history overview.
     * @throws ParseException is thrown from endRecordingButton
     */
    public void updateRecordButton() throws ParseException {
        String text = recordButton.getText();
        if ("RECORD".equals(text)) {
            mainTabService.startRecordingButton(projectsTabController);
        } else if ("END".equals(text)) {
            mainTabService.endRecordingButton(historyTabController);
        }
    }

    /**
     * If button shows "History", then change window from Graph tab to History tab and vice versa.
     */
    public void changeHistoryAndGraph() {
        String text = historyAndGraphButton.getText();
        if ("GRAPH".equals(text)) {
            mainTabService.changeRightWindow(currentTab, graphTab);
            historyAndGraphButton.setText("HISTORY");
            currentTab = graphTab;
        } else if ("HISTORY".equals(text)) {
            mainTabService.changeRightWindow(currentTab, historyTab);
            historyAndGraphButton.setText("GRAPH");
            currentTab = historyTab;
        }
    }

    public void injectHistoryTab() throws IOException {
        var loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/HistoryTab.fxml"));
        Parent content = loader.load();
        historyTab = (AnchorPane) content;
        historyTab.setDisable(true);
        historyTab.setOpacity(0);
        historyTabController = loader.getController();
        historyTabController.init(this);
        rightWindow.getChildren().add(content);
        historyTabController.showByTime(Integer.MAX_VALUE);
    }

    public void changeToAutotrackTab() {
        mainTabService.changeRightWindow(currentTab, autotrackTab);
        currentTab = autotrackTab;
    }

    public void injectAutotrackTab() throws IOException {
        var loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/AutotrackTab.fxml"));
        Parent content = loader.load();
        autotrackTab = (AnchorPane) content;
        rightWindow.getChildren().add(content);
        autotrackTab.setOpacity(0);
        autotrackTab.setDisable(true);
        autotrackTabController = loader.getController();
        autotrackTabController.init(this);
    }
}


