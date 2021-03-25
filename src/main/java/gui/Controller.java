package gui;

import data.fileAccess;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import logic.timeCalculator;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

/**
 * Controller class is made for functionality of the UI elements.
 */
public class Controller {

    @FXML
    BarChart<String, Number> projectGraph;

    @FXML
    private Label uiButton;

    @FXML
    private ProjectsTabController projectsTabController; // if we need to get something from projectsTabController
    // Not 100% sure if this is necessary

    // File to read from.
    private final File file = new File("records.csv");

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
        var currentTime = new timeCalculator();
        // Object from data layer for reading and writing to file.
        var data = new fileAccess();
        // Getting current time.
        LocalTime timeStampToShow = currentTime.returnTime();
        // Adding that time to file.
        data.addRecordToFile(file, timeStampToShow);
        // Updating graph based on the entry.
        updateGraph(data.howManyRecords(file));
        // For lulz forcing label "Project X" to show current time.
        uiButton.setText(String.valueOf(timeStampToShow));
    }



    public void addProject() {
        String projectName = GUIElemHandler.textDialog("New Project", "Project", "Add a new project:");

        TreeItem<String> mainProjectTree = projectsTabController.getMainTree();
        projectsTabController.createProject(mainProjectTree,  projectName);
    }

    /**
     * Method for updating the graph.
     * @param numberOfRecords how many entries in a file.
     */
    public void updateGraph(int numberOfRecords) {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Entry:" + numberOfRecords);
        series1.getData().add(new XYChart.Data("project", numberOfRecords));
        projectGraph.getData().add(series1);
    }


}
