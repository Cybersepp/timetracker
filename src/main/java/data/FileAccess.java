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

import java.io.File;
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

    public static Map<String, List<ProjectTreeItem>> getData() {

        System.out.println("help");
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("data.json");

        try {
//            Map<String, List<ProjectTreeItem>> mainMap = objectMapper.readValue(file, new TypeReference<Map<String, List<ProjectTreeItem>>>() {});
            Map<String, List<ProjectTreeItem>> dataMap = objectMapper.readValue(Paths.get("data.json").toFile(),
                    new TypeReference<Map<String, List<ProjectTreeItem>>>(){});

            List<ProjectTreeItem> projects = dataMap.get("projects");

            projects.forEach((project) -> System.out.println(project.getJuniors()));
            return null;
        } catch (IOException e) {
            System.out.println("Error during file reading: " + e.getMessage());
        }
        return null;

    }
}
