package gui.controllers;

import data.DataHandler;
import data.FileAccess;
import data.Record;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import logic.timer.Timer;
import logic.treeItems.TaskTreeItem;

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


    // ---- WINDOWS ----

    @FXML
    private AnchorPane graphTab;

    @FXML
    private AnchorPane historyTab;


    // ---- UI ELEMENTS ----

    @FXML
    private Button historyAndGraphButton;

    @FXML
    private Button recordButton;

    @FXML
    private Label timerId;

    @FXML
    private Rectangle timeLine;

    Timer timer = null;

    FillTransition fill = null;

    // ---- DAO ----

    private final Record record = new Record();

    private final DataHandler dataHandler = new DataHandler();

    @FXML
    private void initialize() {
        // I know it's retarded, sorry.
        historyTabController.init(this);
        projectsTabController.init(this);
        historyTab.setOpacity(0);
        historyTab.setDisable(true);
        graphTabController.initUpdateGraph();

    }

    public void updateRecordButton() {
        //TODO lookin hella ugly, gotta move it out of here!
        switch (recordButton.getText()) {

            case "RECORD":
                if (projectsTabController.selectItem() == null || !projectsTabController.selectItem().getClass().equals(TaskTreeItem.class)
                         ) {
                    //TODO if no project is selected create a project and task and start recording there
                    //TODO if project is selected without task, create task and start recording there
                    //TODO also display a quick message (that would disappear after 1-2s) (possible?)

                    break;
                }
                recordButton.setText("END");
                dataHandler.setCurrentlyChosenTask((TaskTreeItem) projectsTabController.selectItem());
                record.setRecordStart();
                startTimer();
                break;

            case "END":
                graphTabController.clearGraph();
                TaskTreeItem currentTask = dataHandler.getCurrentlyChosenTask();
                recordButton.setText("RECORD");
                record.setRecordEnd();
                stopTimer();
                String recordInfo = record.getRecordInfo();
                currentTask.getRecords().add(recordInfo);
                FileAccess.saveData();
                graphTabController.initUpdateGraph();
                break;
        }
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
        // I know it's retarded. sorry.
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

    public HistoryTabController getHistoryTabController() {
        return historyTabController;
    }

    public ProjectsTabController getProjectsTabController() {
        return projectsTabController;
    }

    public GraphTabController getGraphTabController() {
        return graphTabController;
    }
}


