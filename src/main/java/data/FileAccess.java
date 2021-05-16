package data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import gui.controllers.ProjectsTabController;
import gui.popups.notification.ErrorPopup;
import logic.treeItems.ProjectTreeItem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileAccess {

    public FileAccess() {
        // util methods
    }

    public static void saveData() {

        List<ProjectTreeItem> activeProjects = new ArrayList<>(ProjectsTabController.getActiveRoot().getJuniors());
        List<ProjectTreeItem> archivedProjects = new ArrayList<>(ProjectsTabController.getArchivedRoot().getJuniors());
        activeProjects.addAll(archivedProjects);
        Map<String, List<ProjectTreeItem>> mainMap = Map.of("projects", activeProjects);

        var objectMapper = new ObjectMapper();

        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(Paths.get("data.json").toFile(), mainMap);
        } catch (IOException e) {
            new ErrorPopup("Something went wrong when writing to file").popup();
        }
    }

    public static Map<String, List<ProjectTreeItem>> getData() {

        var objectMapper = new ObjectMapper();

        var dataFile = new File("data.json");

        try {
            if (dataFile.createNewFile() || dataFile.length() == 0) {
                return null; // when the file is empty or no file existed before
            }
            return objectMapper.readValue(dataFile, new TypeReference<>() {});
        } catch (IOException e) {
            new ErrorPopup("Something went wrong when reading from file").popup();
            return null;
        }
    }
}
