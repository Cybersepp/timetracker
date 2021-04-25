package data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import gui.controllers.ProjectsTabController;
import gui.popups.notification.ErrorPopup;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.TaskTreeItem;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileAccess {

    public FileAccess() {
        // util methods
    }

    public static void saveData() {
        try {
            Map<String, Object> dataMap = new HashMap<>();

            List<ProjectTreeItem> currentProjects = new ArrayList<>(ProjectsTabController.getProjects().getJuniors());
            currentProjects.addAll(ProjectsTabController.getArchived().getJuniors());

            for (ProjectTreeItem project : currentProjects) {
                dataMap.put(project.getValue(), getProjectMap(project));
            }

            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(Paths.get("data.json").toFile(), dataMap);
        } catch (Exception e) {
            new ErrorPopup("Could not write to file: " + e);
        }
    }

    public static Map<String, Object> getProjectMap(ProjectTreeItem project) {
        Map<String, Object> taskMap = new HashMap<>();

        List<TaskTreeItem> projectTasks = project.getJuniors();

        taskMap.put("isArchived", project.isArchived());
        taskMap.put("Tasks", getTaskMap(project));

        return taskMap;
    }

    public static Map<String, Object> getTaskAttributesMap(TaskTreeItem task) {
        Map<String, Object> taskAttributesMap = new HashMap<>();

        taskAttributesMap.put("isDone", task.isDone());
        taskAttributesMap.put("Records", task.getRecords());

        return taskAttributesMap;
    }

    public static Map<String, Map<String, Object>> getTaskMap(ProjectTreeItem project) {
        Map<String, Map<String, Object>> taskMap = new HashMap<>();
        List<TaskTreeItem> projectTaskList = project.getJuniors();

        projectTaskList.forEach((t -> taskMap.put(t.getValue(), getTaskAttributesMap(t))));

        return taskMap;
    }

    public static Map<String, Map<String, Object>> getProjectData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //TODO check if the file exists and then return null here instead of Exception


            return objectMapper.readValue(Paths.get("data.json").toFile(),
                    new TypeReference<>() {
                    });
        } catch (IOException e) {
            return null;
            // TODO Should it also have a WarningPopup occur if some exception other than no file found occurs?
        }
    }
}
