package logic.services;

import data.DataHandler;
import data.FileAccess;
import data.Recording;
import gui.controllers.GraphTabController;
import gui.controllers.HistoryTabController;
import gui.controllers.ProjectsTabController;
import gui.popups.notification.ErrorPopup;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import logic.timer.Timer;
import logic.treeItems.TaskTreeItem;

public class MainTabService {

    private final DataHandler dataHandler = new DataHandler();
    private Timer timer;
    private FillTransition fill;
    private Recording recording;
    private final Button recordButton;
    private final Rectangle timeLine;
    private final Label timerId;

    public MainTabService(Button recordButton, Rectangle timeLine, Label timerId) {
        this.recordButton = recordButton;
        this.timeLine = timeLine;
        this.timerId = timerId;
    }

    /**
     * Method that checks if a task has been selected to start a recording on that
     */
    public void startRecordingButton(ProjectsTabController projectsTabController) {

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
     * Method for ending a recording
     *
     */
    public void endRecordingButton(HistoryTabController historyTabController, GraphTabController graphTabController) {
        TaskTreeItem currentTask = dataHandler.getCurrentlyChosenTask();
        recordButton.setText("RECORD");
        recording.setRecordEnd();
        stopTimer();
        currentTask.getRecordings().add(recording);
        addToHistory(historyTabController, recording);
        FileAccess.saveData();
        historyTabController.showByTime(graphTabController.howManyDaysToShow(), graphTabController);
    }


    /**
     * Method for starting a recording
     *
     * @param selectedTask - The task that is to start recording
     */
    private void startRecording(TaskTreeItem selectedTask) {
        recordButton.setText("END");
        dataHandler.setCurrentlyChosenTask(selectedTask);
        recording = new Recording(selectedTask); //creating a new Recording
        recording.setRecordStart();
        startTimer();
    }

    private void animateRecordButton() {
        fill.setAutoReverse(true);
        fill.setCycleCount(Animation.INDEFINITE);
        fill.play();
    }

    private void animateStopRecordButton() {
        fill.stop();
    }

    private void stopTimer() {
        timer.endTimer();
        timeLine.setFill(Color.GREY);
        animateStopRecordButton();
    }

    private void startTimer() {
        timer = new Timer(timerId);
        timer.startTimer();
        fill = new FillTransition(Duration.millis(1500), timeLine, Color.GREY, Color.RED);
        animateRecordButton();
    }

    /**
     * Adds new record entry to the history tab's table view.
     *
     * @param recording - the recording to be added
     */
    public void addToHistory(HistoryTabController historyTabController, Recording recording) {
        historyTabController.addRecord(recording);
    }

    /**
     * Method for switching between tabs of the AnchorPane "rightSideWindow".
     *
     * @param tabToRemove AnchorPane to be removed.
     * @param tabToAdd    AnchorPane to be added.
     */
    public void changeRightWindow(AnchorPane tabToRemove, AnchorPane tabToAdd) {
        tabToRemove.setOpacity(0);
        tabToRemove.setDisable(true);
        tabToAdd.setOpacity(1);
        tabToAdd.setDisable(false);
    }
}
