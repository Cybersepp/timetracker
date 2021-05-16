package logic.services;

import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphTabService {

    private final StackedBarChart<String, Number> projectGraph;
    private final List<XYChart.Series<String, Number>> allSeries = new ArrayList<>();

    public GraphTabService(StackedBarChart<String, Number> projectGraph) {
        this.projectGraph = projectGraph;
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
}
