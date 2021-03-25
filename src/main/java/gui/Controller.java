package gui;

import data.FileAccess;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.TimeCalculator;

import java.io.IOException;
import java.time.LocalTime;

/**
 * Controller class is made for functionality of the UI elements.
 */
public class Controller {

    @FXML
    private Button historyAndGraphButton;

    @FXML
    private Label uiButton;

    @FXML
    private ProjectsTabController projectsTabController; // if we need to get something from projectsTabController

    @FXML
    private GraphTabController graphTabController;

    @FXML
    private void initialize() {

    }
    /**
     * Method updateButton gets a current time by calling "returnTime" method from data.fileAccess class.
     * Then it saves current time as an entry to the "records.csv" file.
     * Then it updates the graph based on how many entries there are in the file by calling
     * data.howManyRecords from fileAccess class and adds that entry number to the graph as an independent bar.
     * @throws IOException meh
     */
    public void updateButton() throws IOException {
        // Object from data layer for reading and writing to file.
        var data = new FileAccess();
        // Getting current time.
        LocalTime timeStampToShow = TimeCalculator.returnTime();
        // Adding that time to file.
        data.addRecordToFile(timeStampToShow);
        // Updating graph based on the entry.
        notifyGraphTab();
    }
    public void notifyGraphTab()  {
        graphTabController.updateGraph();
        System.out.println(graphTabController);

    }

    public void changeHistoryAndGraph() {
        //TODO Change graph tab to history tab.
        switch (historyAndGraphButton.getText()) {
            case "GRAPH": historyAndGraphButton.setText("HISTORY"); break;
            case "HISTORY": historyAndGraphButton.setText("GRAPH"); break;
        }



    }
}
