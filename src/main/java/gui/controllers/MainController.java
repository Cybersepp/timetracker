package gui.controllers;

import data.DataHandler;
import data.FileAccess;
import data.Record;
import data.tableview.RecordEntryData;
import data.Recording;
import gui.popups.notification.ErrorPopup;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import logic.timer.Timer;
import logic.treeItems.TaskTreeItem;

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

    Timer timer = null;

    FillTransition fill = null;

    // ---- DAO ----

    private Recording recording;

    private final DataHandler dataHandler = new DataHandler();

    @FXML
    private void initialize() throws ParseException, IOException {
        projectsTabController.init(this);
        injectHistoryTab();
        injectAutotrackTab();

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
            startRecordingButton();
        } else if ("END".equals(text)) {
            endRecordingButton();
        }
    }

    /**
     * Method that checks if a task has been selected to start a recording on that
     */
    private void startRecordingButton() {
        //TODO Needs some upgrades
        // - if no project is selected create a project and task and start recording there
        // - if project is selected without task, create task and start recording there
        // - also display a quick message (that would disappear after 1-2s) (possible?)
        final var selectedItem = projectsTabController.selectItem();
        if (selectedItem == null || !selectedItem.getClass().equals(TaskTreeItem.class) ||
                selectedItem.isArchived() || ((TaskTreeItem) selectedItem).isDone()
        ) {
            new ErrorPopup("You have not selected an unfinished task!").popup();
            return;
        }
        startRecording((TaskTreeItem) selectedItem);
    }

    /**
     * Method for starting a recording
     * @param selectedTask - The task that is to start recording
     */
    private void startRecording(TaskTreeItem selectedTask) {
        recordButton.setText("END");
        dataHandler.setCurrentlyChosenTask(selectedTask);
        recording = new Recording(selectedTask); //creating a new Recording
        recording.setRecordStart();
        startTimer();
    }

    /**
     * Method for ending a recording
     * @throws ParseException is thrown if can't parse string to date format.
     */
    private void endRecordingButton() throws ParseException {
        TaskTreeItem currentTask = dataHandler.getCurrentlyChosenTask();
        recordButton.setText("RECORD");
        recording.setRecordEnd();
        stopTimer();
        currentTask.getRecordings().add(recording);
        addToHistory(recording);
        FileAccess.saveData();
        historyTabController.showByTime(Integer.MAX_VALUE);
    }


    /**
     * If button shows "History", then change window from Graph tab to History tab and vice versa.
     */
    public void changeHistoryAndGraph() {
        switch (historyAndGraphButton.getText()) {
            case "GRAPH":
                changeRightWindow(historyTab, graphTab);
                historyAndGraphButton.setText("HISTORY");
                break;
            case "HISTORY":
                changeRightWindow(graphTab, historyTab);
                historyAndGraphButton.setText("GRAPH");
                break;
        }
    }

    /**
     * Method for switching between tabs of the AnchorPane "rightSideWindow".
     *
     * @param tabToRemove AnchorPane to be removed.
     * @param tabToAdd    AnchorPane to be added.
     */
    public void changeRightWindow(AnchorPane tabToRemove, AnchorPane tabToAdd) {
        autotrackTab.setDisable(true);
        autotrackTab.setOpacity(0);
        tabToRemove.setOpacity(0);
        tabToRemove.setDisable(true);
        tabToAdd.setOpacity(1);
        tabToAdd.setDisable(false);
    }

    public void animateRecordButton() {
        fill.setAutoReverse(true);
        fill.setCycleCount(Animation.INDEFINITE);
        fill.play();
    }

    public void animateStopRecordButton() {
        fill.stop();
    }

    public void stopTimer() {
        timer.endTimer();
        timeLine.setFill(Color.GREY);
        animateStopRecordButton();
    }

    public void startTimer() {
        timer = new Timer(timerId);
        timer.startTimer();
        fill = new FillTransition(Duration.millis(1500), timeLine, Color.GREY, Color.RED);
        animateRecordButton();
    }

    /**
     * Adds new record entry to the history tab's table view.
     * @param recording - the recording to be added
     */
    public void addToHistory(Recording recording) {
        historyTabController.addRecord(recording);
    }

    public void injectHistoryTab() throws IOException, ParseException {
        FXMLLoader loader = new FXMLLoader();
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
        historyTab.setDisable(true);
        historyTab.setOpacity(0);
        graphTab.setDisable(true);
        graphTab.setOpacity(0);
        autotrackTab.setOpacity(1);
        autotrackTab.setDisable(false);
    }

    public void injectAutotrackTab() throws IOException {
        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("/gui/AutotrackTab.fxml"));
        Parent content = loader2.load();
        autotrackTab = (AnchorPane) content;
        rightWindow.getChildren().add(content);
        autotrackTab.setOpacity(0);
        autotrackTab.setDisable(true);
        autotrackTabController = loader2.getController();
        autotrackTabController.init(this);
    }
}


