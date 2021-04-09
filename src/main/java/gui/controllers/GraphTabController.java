package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.util.Map;

import static data.FileAccess.getProjectData;


public class GraphTabController {

    @FXML
    BarChart<String, Number> projectGraph;

    @FXML
    private void initialize() {

    }
    /**
     * Method for updating the graph.
     */
    public void updateGraph(Map<String, Float> projectNameAndTime) throws IOException {

        projectNameAndTime.forEach((projectName,time) -> {
            XYChart.Series series = new XYChart.Series();
            series.getData().add(new XYChart.Data(projectName, time));
            projectGraph.getData().add(series);
        });
    }

    public void clearGraph() {
        projectGraph.getData().clear();
    }

}
