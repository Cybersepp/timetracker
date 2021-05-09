package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import logic.treeItems.*;
import java.util.*;

public class GraphTabController {

    List<XYChart.Series> allSeries = new ArrayList<>();

    @FXML
    private ProjectsTabController projectsTabController;

    @FXML
    StackedBarChart<String, Number> projectGraph;

    @FXML
    private Label graphLabel;

    @FXML
    private void initialize() {

    }

    public void updateGraph(Map<String, Integer> projectData) {
        clearGraph();
        for (String project : projectData.keySet()) {
            XYChart.Series series = new XYChart.Series();
            series.getData().add(new XYChart.Data(project, projectData.get(project)));
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

