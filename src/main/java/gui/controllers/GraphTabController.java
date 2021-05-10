package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

import java.util.*;

public class GraphTabController {

    List<XYChart.Series<String, Number>> allSeries = new ArrayList<>();

    @FXML
    private ProjectsTabController projectsTabController;

    @FXML
    StackedBarChart<String, Number> projectGraph;

    @FXML
    private Label graphLabel;

    @FXML
    private void initialize() {
        // Doesn't do anything on start
    }

    public void updateGraph(Map<String, Integer> projectData) {
        clearGraph();
        for (Map.Entry<String, Integer> project : projectData.entrySet()) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>(project.getKey(), project.getValue()));
            allSeries.add(series);
            projectGraph.getData().add(series);
        }
    }

    public void clearGraph() {
        projectGraph.getData().clear();
    }

    public void setGraphLabel(String text) {
        graphLabel.setText(text);
    }
}

