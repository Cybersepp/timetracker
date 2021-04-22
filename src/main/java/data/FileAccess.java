package data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import gui.controllers.ProjectsTabController;
import gui.popups.WarningPopup;
import logic.Treeitems.ProjectTreeItem;
import logic.Treeitems.TaskTreeItem;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.*;

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
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(Paths.get("data.json").toFile(), dataMap);
        } catch (Exception e) {
            new WarningPopup("Could not write to file: " + e);
        }

    }

    public static Map<String, Object> getTaskMap(ProjectTreeItem project) {
        Map<String, Object> taskMap = new HashMap<>();

        List<TaskTreeItem> projectTasks = project.getJuniors();

        taskMap.put("isArchived", project.isArchived());

        for (TaskTreeItem task : projectTasks) {
            taskMap.put(task.getValue(), getTaskAttributesMap(task));
        }

        return taskMap;
    }

    public static Map<String, Object> getTaskAttributesMap(TaskTreeItem task) {
        Map<String, Object> taskAttributesMap = new HashMap<>();

        taskAttributesMap.put("isDone", task.isDone());
        taskAttributesMap.put("Records", task.getRecords());

        return taskAttributesMap;
    }

    public static Map<String, Map<String, List<String>>> getProjectData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(Paths.get("data.json").toFile(),
                    new TypeReference<>() {
                    });
        } catch (IOException e) {
            return null;
        }
    }

}
