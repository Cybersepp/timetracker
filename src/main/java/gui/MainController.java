package gui;

import data.FileAccess;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import logic.TimeCalculator;
import java.io.IOException;
import java.time.LocalTime;

/**
 * MainController class is made for functionality of the UI elements.
 */
public class MainController {

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
    private void initialize() {
        // It's not pretty, but upon initialization it removes history tab so they wouldn't overlap.
        rightSideWindow.getChildren().remove(historyTab);

    }
    /**
     * Method updateButton saves an entry to "records.csv" and then updates the graph based on the entry.
     * (Right now every entry is equal to the other).
     * @throws IOException In case something goes wrong with IO.
     */
    public void updateButton() throws IOException {
        // Object from data layer for reading and writing to file.
        var data = new FileAccess();
        // Getting current time.
        LocalTime timeStampToShow = TimeCalculator.returnTime();
        // Adding that time to file.
        data.addRecordToFile(timeStampToShow);
        // Updating graph based on the entry.
        graphTabController.updateGraph();
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
                historyAndGraphButton.setText("HISTORY");
                changeRightWindow(historyTab, graphTab);
                break;
            case "HISTORY":
                historyAndGraphButton.setText("GRAPH");
                changeRightWindow(graphTab, historyTab);
                break;
        }
    }

    /**
     * Method for switching between tabs of the Anchorpane "rightSideWindow".
     * @param tabToRemove Anchorpane to be removed.
     * @param tabToAdd Anchorpane to be added.
     */
    public void changeRightWindow(AnchorPane tabToRemove, AnchorPane tabToAdd) {
        rightSideWindow.getChildren().remove(tabToRemove);
        rightSideWindow.getChildren().add(tabToAdd);
    }
}
