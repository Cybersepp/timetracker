package data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import gui.controllers.ProjectsTabController;
import gui.popups.ErrorPopup;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.TaskTreeItem;

import java.io.IOException;
import java.nio.file.Paths;
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

            List<ProjectTreeItem> currentProjects = ProjectsTabController.getProjects().getJuniors();

            for (ProjectTreeItem project : currentProjects) {
                dataMap.put(project.getValue(), getTaskMap(project));
            }

            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(Paths.get("data.json").toFile(), dataMap);
        } catch (Exception e) {
            new ErrorPopup("Could not write to file: " + e);
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

    public static Map<String, Map<String, List<String>>> getProjectData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(Paths.get("data.json").toFile(),
                    new TypeReference<>() {
                    });
        } catch (IOException e) {
            return null; // TODO what does this catch method capture? Should it also have a WarningPopup occur?
        }
    }
}
