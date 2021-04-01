package gui;

import data.DataHandler;
import data.FileAccess;
import data.Record;
import data.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import logic.TimeCalculator;

import java.io.IOException;
import java.time.LocalTime;

/**
 * MainController class is made for functionality of the UI elements.
 */
public class MainController {


    // ---- CONTROLLER INSTANCES ----

    private final Record record = new Record();

    private Task currentlyRecordedTask;

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


    @FXML
    private void initialize() {
        // I know it's retarded, sorry.
        historyTab.setOpacity(0);
        historyTab.setDisable(true);


    }
    /**
     * Method updateButton saves an entry to "records.csv" and then updates the graph based on the entry.
     * (Right now every entry is equal to the other).
     * @throws IOException In case something goes wrong with IO.
     */
    public void updateButton() throws IOException {

        recordButton.setDisable(true);
        recordButton.setOpacity(0);

        record.setRecordStart();

        currentlyRecordedTask = DataHandler.currentlyChosenTask;

        endRecordButton.setDisable(false);
        endRecordButton.setOpacity(1);
    }

    public void updateEndButton() {
        endRecordButton.setDisable(true);
        endRecordButton.setOpacity(0);

        record.setRecordEnd();

        String recordInfo = record.getRecordInfo();
        currentlyRecordedTask.addRecord(recordInfo);

        System.out.println("Record: " + recordInfo + " was added to task " +  currentlyRecordedTask.getName() +
                " which belongs to project " +  currentlyRecordedTask.getBelongs());

        FileAccess.saveData();

        recordButton.setDisable(false);
        recordButton.setOpacity(1);

    }


//    public void addProject() {
//        String projectName = GUIElemHandler.textDialog("New Project", "Project", "Add a new project:");
//
//        TreeItem<String> mainProjectTree = projectsTabController.getMainTree();
//        projectsTabController.createProject(mainProjectTree,  projectName);
//    }

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
     * Method for switching between tabs of the Anchorpane "rightSideWindow".
     * @param tabToRemove Anchorpane to be removed.
     * @param tabToAdd Anchorpane to be added.
     */
    public void changeRightWindow(AnchorPane tabToRemove, AnchorPane tabToAdd) {
        // I know it's retarded. sorry.
        tabToRemove.setOpacity(0);
        tabToRemove.setDisable(true);
        tabToAdd.setOpacity(1);
        tabToAdd.setDisable(false);

    }
}
