package gui;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;

public class GraphTabController {
    @FXML
    BarChart<String, Number> projectGraph;

    @FXML
    private void initialize() {
    }


    /**
     * Method for updating the graph.
     */
    public void updateGraph() {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Entry:");
        series1.getData().add(new XYChart.Data("project", 50));
        projectGraph.getData().add(series1);
    }

    public void labelClicked(MouseEvent mouseEvent) {
        updateGraph();
        System.out.println(this);
    }
}
