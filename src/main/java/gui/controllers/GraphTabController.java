package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import logic.services.GraphTabService;

import java.util.*;

public class GraphTabController {

    @FXML
    StackedBarChart<String, Number> projectGraph;

    @FXML
    private Label graphLabel;

    private GraphTabService graphTabService;

    @FXML
    private void initialize() {
        graphTabService = new GraphTabService(projectGraph);
    }

    public void updateGraph(Map<String, Integer> projectData) {
        graphTabService.updateGraph(projectData);
    }

    public void setGraphLabel(String text) {
        graphLabel.setText(text);
    }

    public String getGraphLabelText() {
        return graphLabel.getText();
    }
}

