package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import logic.Treeitems.ProjectTreeItem;
import logic.Treeitems.RootTreeItem;
import logic.Treeitems.TaskTreeItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static data.FileAccess.getProjectData;


public class GraphTabController {

    //TODO right now it reads from file after every update of the record but instead it should just update specific xychart value.

    List<XYChart.Series> allSeries = new ArrayList<>();

    @FXML
    private ProjectsTabController projectsTabController;

    @FXML
    BarChart<String, Number> projectGraph;

    @FXML
    private void initialize() {

    }
    /**
     * Method for updating the graph.
     */
    public void initUpdateGraph()  {
        RootTreeItem root = projectsTabController.getProjects();
        List<ProjectTreeItem> projects = root.getJuniors();

        projects.forEach(projectTreeItem -> {
            float projectTime = calculateProjectTime(projectTreeItem);
            String projectName = projectTreeItem.getValue();
            System.out.println(projectName + projectTime);
            XYChart.Series series = new XYChart.Series();
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

    public void clearGraph() {
        projectGraph.getData().clear();
    }



}
