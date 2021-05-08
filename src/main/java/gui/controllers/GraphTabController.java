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


// Obsolete method for updating the graph, though might need it later.
   /* public void initUpdateGraph()  {
        clearGraph();
        RootTreeItem root = projectsTabController.getProjects();
        List<ProjectTreeItem> projects = root.getJuniors();
        projects.forEach(projectTreeItem -> {
            XYChart.Series series = new XYChart.Series();
            float projectTime = calculateProjectTime(projectTreeItem);
            String projectName = projectTreeItem.getValue();
            series.getData().add(new XYChart.Data(projectName, projectTime));
            allSeries.add(series);
            projectGraph.getData().add(series);

        });
    }

    public float calculateProjectTime(ProjectTreeItem project) {

        float time = 0;
        List<TaskTreeItem> tasks = project.getJuniors();

        for (TaskTreeItem task : tasks) {
            List<String> records = task.getRecords();
            for (String record : records) {
                String[] recordData = record.split(",");
                time += Float.parseFloat(recordData[2]);
            }
        }
        return time;
    }*/
