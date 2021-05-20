package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import logic.services.GraphTabService;

import java.math.BigDecimal;
import java.util.*;

public class GraphTabController {

    @FXML
    StackedBarChart<String, BigDecimal> projectGraph;

    @FXML
    private Label graphLabel;

    private GraphTabService graphTabService;

    @FXML
    private void initialize() {
        graphTabService = new GraphTabService(projectGraph);
    }

    public void updateGraph(Map<String, BigDecimal> projectData) {
        graphTabService.updateGraph(projectData);
    }

    public void setGraphLabel(String text) {
        graphLabel.setText(text);
    }

    public int howManyDaysToShow() {
        String labelText = graphLabel.getText();
        switch (labelText) {
            case "Last 7 days": return 7;
            case "Last 30 days": return 30;
            case "Last 365 days": return 365;
            default: return Integer.MAX_VALUE;
        }
    }

    public void showInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Graph tab information");
        alert.setHeaderText(null);
        alert.setContentText("Y-axis of the graph shows time in hours.\n" +
                "Switch between time intervals from the 'GRAPH' button.\n" +
                "Display project's tasks by right clicking it and selecting 'Show details'.\n" +
                "Click on the table header to sort the items by that column.");
        alert.showAndWait();
    }
}

