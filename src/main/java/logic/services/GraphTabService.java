package logic.services;

import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import logic.graph.BarChartToolTip;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphTabService {

    private final StackedBarChart<String, BigDecimal> projectGraph;
    private final List<XYChart.Series<String, BigDecimal>> allSeries = new ArrayList<>();


    public GraphTabService(StackedBarChart<String, BigDecimal> projectGraph) {
        this.projectGraph = projectGraph;
    }


    /**
     * Method for updating the graph by deleting the previous one and constructing a new one.
     * @param projectData data of projects for graph to be constructed.
     */
    public void updateGraph(Map<String, BigDecimal> projectData) {
        clearGraph();

        // Creating a new graph.
        for (Map.Entry<String, BigDecimal> project : projectData.entrySet()) {
            XYChart.Series<String, BigDecimal> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>(project.getKey(), project.getValue()));
            allSeries.add(series);
            projectGraph.getData().add(series);
        }
        // Adding tooltips for each bar.
        projectGraph.getData().forEach(data -> {
            var barCharts = data.getData();
            for (XYChart.Data<String, BigDecimal> chartData : barCharts) {
                Tooltip tooltip = new BarChartToolTip((chartData.getYValue().multiply(BigDecimal.valueOf(3600))).toString(), chartData.getXValue());
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
