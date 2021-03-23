package gui;

import data.FileAccess;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.stage.Stage;
import logic.TimeCalculator;


import java.io.IOException;
import java.time.LocalTime;

/**
 * Controller class is made for functionality of the UI elements.
 */
public class Controller {


    @FXML
    private Label uiButton;

    @FXML
    private ProjectsTabController projectsTabController; // if we need to get something from projectsTabController
    // Not 100% sure if this is necessary

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
        // Object from logic layer for calculating current time.
        var currentTime = new TimeCalculator();
        // Object from data layer for reading and writing to file.
        var data = new FileAccess();
        // Getting current time.
        LocalTime timeStampToShow = currentTime.returnTime();
        // Adding that time to file.
        data.addRecordToFile(timeStampToShow);
        // Updating graph based on the entry.
        notifyGraphTab();
    }
    public void notifyGraphTab() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GraphTab.fxml"));
        loader.load();
        GraphTabController graphController = loader.getController();
        graphController.updateGraph();
        System.out.println(graphController);

    }









}
