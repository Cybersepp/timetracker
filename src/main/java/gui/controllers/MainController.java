package gui.controllers;

import data.DataHandler;
import data.FileAccess;
import data.Record;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import logic.Treeitems.TaskTreeItem;

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
    private void initialize()  {
        // I know it's retarded, sorry.

        historyTab.setOpacity(0);
        historyTab.setDisable(true);
        graphTabController.initUpdateGraph();
    }

    public void updateRecordButton()  {

        switch (recordButton.getText()) {

            case "RECORD":
                if (projectsTabController.selectItem().getClass().equals(TaskTreeItem.class)) {
                    recordButton.setText("END");
                    dataHandler.setCurrentlyChosenTask((TaskTreeItem) projectsTabController.selectItem());
                    record.setRecordStart();
                }
                else {
                    // TODO use logger
                    System.out.println("No task has been selected");
                }
                break;

            case "END":
                graphTabController.clearGraph();
                TaskTreeItem currentTask = dataHandler.getCurrentlyChosenTask();
                recordButton.setText("RECORD");
                record.setRecordEnd();
                String recordInfo = record.getRecordInfo();
                currentTask.getRecords().add(recordInfo);
                //System.out.println("Record: " + recordInfo + " was added to task " +  currentTask.getValue() +
                //        " which belongs to project " +  currentTask.getParent().getValue());
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
