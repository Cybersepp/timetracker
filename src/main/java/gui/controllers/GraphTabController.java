package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import logic.treeItems.*;
import java.util.*;

public class GraphTabController {

    //TODO right now it reads from file after every update of the record but instead it should just update specific xychart value.

    List<XYChart.Series> allSeries = new ArrayList<>();



    @FXML
    private ProjectsTabController projectsTabController;

    @FXML
    StackedBarChart<String, Number> projectGraph;

    @FXML
    private void initialize() {

    }
    /**
     * Method for updating the logic.graph.
     */
    public void initUpdateGraph()  {
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

}
