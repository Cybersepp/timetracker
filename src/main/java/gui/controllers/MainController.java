package gui.controllers;

import data.DataHandler;
import data.FileAccess;
import data.Record;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import logic.treeItems.TaskTreeItem;

import java.io.IOException;

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

    // ---- WINDOWS ----

    @FXML
    private AnchorPane rightSideWindow;

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
    private Button endRecordButton;

    // ---- DAO ----

    private final Record record = new Record();

    private final DataHandler dataHandler = new DataHandler();

    @FXML
    private void initialize() throws IOException {
        // I know it's retarded, sorry.

        historyTab.setOpacity(0);
        historyTab.setDisable(true);
        graphTabController.updateGraph();


    }

    public void updateButton() { // TODO this updateButton should be one method only, not two separate

        if (projectsTabController.selectItem().getClass().equals(TaskTreeItem.class)) {
            recordButton.setDisable(true);
            recordButton.setOpacity(0);

            dataHandler.setCurrentlyChosenTask((TaskTreeItem) projectsTabController.selectItem());
            record.setRecordStart();

            endRecordButton.setDisable(false);
            endRecordButton.setOpacity(1);
        }

        else {
            System.out.println("No task has been selected");
        }
    }

    public void updateEndButton() throws IOException {

        TaskTreeItem currentTask = dataHandler.getCurrentlyChosenTask();

        graphTabController.clearGraph();
        endRecordButton.setDisable(true);
        endRecordButton.setOpacity(0);

        record.setRecordEnd();

        String recordInfo = record.getRecordInfo();
        currentTask.getRecords().add(recordInfo);

        graphTabController.updateGraph();

        System.out.println("Record: " + recordInfo + " was added to task " +  currentTask.getValue() +
                " which belongs to project " +  currentTask.getParent().getValue());

        FileAccess.saveRecordData();

        recordButton.setDisable(false);
        recordButton.setOpacity(1);

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
     * @param tabToRemove AnchorPane to be removed.
     * @param tabToAdd AnchorPane to be added.
     */
    public void changeRightWindow(AnchorPane tabToRemove, AnchorPane tabToAdd) {
        // I know it's retarded. sorry.
        tabToRemove.setOpacity(0);
        tabToRemove.setDisable(true);
        tabToAdd.setOpacity(1);
        tabToAdd.setDisable(false);

    }
}
