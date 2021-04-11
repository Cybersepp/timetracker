package data;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import gui.controllers.ProjectsTabController;
import gui.popups.WarningPopup;
import logic.Treeitems.ProjectTreeItem;
import logic.Treeitems.TaskTreeItem;

import java.io.*;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.*;

/**
 * fileAccess method takes path of the file and given time and writes in on a new line in .txt file.
 */
public class FileAccess {

    public FileAccess() {
        // util classes
    }

    public static void saveData() {
        try {
            Map<String, Object> dataMap = new HashMap<>();

            List<ProjectTreeItem> currentProjects = ProjectsTabController.getProjects().getJuniors();

            for (ProjectTreeItem project : currentProjects) {
                dataMap.put(project.getValue(), getTaskMap(project));
            }

            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writter = mapper.writer(new DefaultPrettyPrinter());
            writter.writeValue(Paths.get("data.json").toFile(), dataMap);
        } catch (Exception e) {
            new WarningPopup("Could not write to file: " + e);
        }

    }

    public static Map<String, List<String>> getTaskMap(ProjectTreeItem project) {
        Map<String, List<String>> taskMap = new HashMap<>();

        List<TaskTreeItem> projectTasks = project.getJuniors();

        for (TaskTreeItem task : projectTasks) {
            taskMap.put(task.getValue(), task.getRecords());
        }

        return taskMap;


    }

    public static Map<String, Float> getProjectData() throws IOException {

        File records = new File("records.txt");
        if (records.createNewFile()) {
            System.out.println("created new data file");
        }

        // TODO division by 60 is quite ugly, should change it to hours.
        String strCurrentLine;
        Map<String, Float> projects = new HashMap<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(records))) {
            while ((strCurrentLine = fileReader.readLine()) != null) {
                String[] recordProperties = strCurrentLine.split(", ");
                String projectName = recordProperties[0];
                Float timeSpentProject = Float.parseFloat(recordProperties[4]);

                if (projects.containsKey(projectName)) {
                    Float oldTime = projects.get(projectName);
                    projects.replace(projectName, (timeSpentProject / 60) + oldTime);
                } else {
                    projects.put(projectName, timeSpentProject / 60);
                }
            }
            return projects;
        }
    }


}
