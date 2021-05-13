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

        List<ProjectTreeItem> activeProjects = new ArrayList<>(ProjectsTabController.getActiveRoot().getJuniors());
        List<ProjectTreeItem> archivedProjects = new ArrayList<>(ProjectsTabController.getArchivedRoot().getJuniors());
        activeProjects.addAll(archivedProjects);
        Map<String, List<ProjectTreeItem>> mainMap = Map.of("projects", activeProjects);

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(Paths.get("data.json").toFile(), mainMap);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Map<String, List<ProjectTreeItem>> getData() {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(Paths.get("data.json").toFile(),
                    new TypeReference<>() {
                    });
        } catch (IOException e) {
            return null;
        }
    }
}
