package gui;

import data.fileAccess;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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
    private TreeView<String> projectsTree;

    // File to read from.
    private final File file = new File("records.csv");

    @FXML
    void initialize() {

        // Test items for tree view
        TreeItem<String> projects = new TreeItem<>("Projects");

        TreeItem<String> project1 = new TreeItem<>("Project1");
        TreeItem<String> project2 = new TreeItem<>("Project2");
        TreeItem<String> project3 = new TreeItem<>("Project3");
        TreeItem<String> project4 = new TreeItem<>("Project4");
        TreeItem<String> archived = new TreeItem<>("Archived");

        TreeItem<String> project1Task1 = new TreeItem<>("task1");
        TreeItem<String> project1Task2 = new TreeItem<>("task2");
        TreeItem<String> project2Task1 = new TreeItem<>("task3");
        TreeItem<String> project2Task2 = new TreeItem<>("task4");
        TreeItem<String> project3Task1 = new TreeItem<>("task5");
        TreeItem<String> project3Task2 = new TreeItem<>("task6");
        TreeItem<String> project4Task1 = new TreeItem<>("task7");
        TreeItem<String> project4Task2 = new TreeItem<>("task8");

        TreeItem<String> archivedProject1 = new TreeItem<>("ArchivedProject1");
        TreeItem<String> archivedProject2 = new TreeItem<>("ArchivedProject2");

        TreeItem<String> archivedTask1 = new TreeItem<>("ArchivedTask1");
        TreeItem<String> archivedTask2 = new TreeItem<>("ArchivedTask2");
        TreeItem<String> archivedTask3 = new TreeItem<>("ArchivedTask3");
        TreeItem<String> archivedTask4 = new TreeItem<>("ArchivedTask4");

        archivedProject1.getChildren().addAll(archivedTask1, archivedTask2);
        archivedProject2.getChildren().addAll(archivedTask3, archivedTask4);

        project1.getChildren().addAll(project1Task1, project1Task2);
        project2.getChildren().addAll(project2Task1, project2Task2);
        project3.getChildren().addAll(project3Task1, project3Task2);
        project4.getChildren().addAll(project4Task1, project4Task2);
        archived.getChildren().addAll(archivedProject1, archivedProject2);

        projects.getChildren().addAll(project1, project2, project3, project4, archived);

        projectsTree.setShowRoot(false);
        projectsTree.setRoot(projects);
        // Test items end for tree view

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

    /**
     * Method for selecting projects and project tasks and sending out value.
     */
    public void selectItem() {
        TreeItem<String> activity = projectsTree.getSelectionModel().getSelectedItem();
        if (activity != null) {
            System.out.println(activity.getValue());
        }
        // TODO list - Richard
        // TODO return some value that can be used using selectItem() method
        // TODO method createProjectItem
        // TODO method createTaskItem
        // TODO method archiveProject
        // TODO method markTaskAsDone

    }
}
