package data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import gui.controllers.ProjectsTabController;
import gui.popups.notification.ErrorPopup;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.TaskTreeItem;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class FileAccess {

    public FileAccess() {
        // util methods
    }

    public static void saveData() {
        // I think here LinkedHashMap is absolutely useless, but I left it like this in case you still need specifically
        // linked map
        Map<String, List<ProjectTreeItem>> mainMap = Map.of("projects", ProjectsTabController.getActiveRoot().getJuniors());

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(Paths.get("data.json").toFile(), mainMap);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //TODO Would be nice if you could somehow append a single thing somewhere in the data.json for example a task to an existing project
    // - Is it possible?
    // - Is it necessary?
    // - How much time would it save?
    // - Thinking about it because if there are loads of recordings, would the constant overwriting of data make it too slow?
//    public static void saveData() {
//        try {
//            Map<String, Object> dataMap = new LinkedHashMap<>();
//
//            List<ProjectTreeItem> currentProjects = new ArrayList<>(ProjectsTabController.getActiveRoot().getJuniors());
//            currentProjects.addAll(ProjectsTabController.getArchivedRoot().getJuniors());
//
//            for (ProjectTreeItem project : currentProjects) {
//                dataMap.put(project.getValue(), getProjectMap(project));
//            }
//
//            ObjectMapper mapper = new ObjectMapper();
//            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
//            writer.writeValue(Paths.get("data.json").toFile(), dataMap);
//        } catch (Exception e) {
//            new ErrorPopup("Could not write to file: " + e);
//        }
//    }

    public static Map<String, Object> getProjectMap(ProjectTreeItem project) {
        Map<String, Object> taskMap = new LinkedHashMap<>();

        taskMap.put("Tasks", getTaskMap(project));
        taskMap.put("isArchived", project.isArchived());

        return taskMap;
    }

    public static Map<String, Map<String, Object>> getTaskMap(ProjectTreeItem project) {
        Map<String, Map<String, Object>> taskMap = new LinkedHashMap<>();
        List<TaskTreeItem> projectTaskList = project.getJuniors();

        projectTaskList.forEach((t -> taskMap.put(t.getValue(), getTaskAttributesMap(t))));

        return taskMap;
    }

    public static Map<String, Object> getTaskAttributesMap(TaskTreeItem task) {
        Map<String, Object> taskAttributesMap = new LinkedHashMap<>();

        taskAttributesMap.put("isDone", task.isDone());

        var recordings = new ArrayList<String>();
        for (Recording recording : task.getRecordings()) {
            recordings.add(recording.getRecordInfo());
        }
        taskAttributesMap.put("Records", recordings);

        return taskAttributesMap;
    }

    //FIXME idk if this is the best way for this - made by Richard
    public static Map<String, String> getRecordAttributesMap(TaskTreeItem task) {
        Map<String, String> recordMap = new HashMap<>();

        var recordings = task.getRecordings();
        recordings.forEach((recording -> recordMap.put("Record", recording.getRecordInfo())));
        //FIXME this might be broken
        return recordMap;
    }

    public static Map<String, Map<String, Object>> getProjectData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //FIXME check if the file exists and then return null here instead of Exception
            return objectMapper.readValue(Paths.get("data.json").toFile(), new TypeReference<>(){});
        } catch (IOException e) {
            return null;
            // TODO Should it also have a WarningPopup occur if some exception other than no file found occurs?
        }
    }
}
