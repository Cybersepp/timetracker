package logic.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import logic.BarChartToolTip;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphTabService {

    private final StackedBarChart<String, Number> projectGraph;
    private final List<XYChart.Series<String, Number>> allSeries = new ArrayList<>();


    public GraphTabService(StackedBarChart<String, Number> projectGraph) {
        this.projectGraph = projectGraph;
    }

    /**
     * Method for updating the graph by deleting the previous one and constructing a new one.
     * @param projectData data of projects for graph to be constructed.
     */
    public void updateGraph(Map<String, Integer> projectData) {
        clearGraph();

        // Creating a new graph.
        for (Map.Entry<String, Integer> project : projectData.entrySet()) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>(project.getKey(), project.getValue()));
            allSeries.add(series);
            projectGraph.getData().add(series);
        }
        // Adding tooltips for each bar.
        projectGraph.getData().forEach(data -> {
            var barCharts = data.getData();
            for (XYChart.Data<String, Number> chartData : barCharts) {
                Tooltip tooltip = new BarChartToolTip(chartData.getYValue().toString(), chartData.getXValue());
                Tooltip.install(chartData.getNode(), tooltip);
            }
        });
    }

    /**
     * Method for deleting graph data.
     */
    public void clearGraph() {
        projectGraph.getData().clear();
    }
}
