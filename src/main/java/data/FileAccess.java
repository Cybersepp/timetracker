package data;

import gui.controllers.ProjectsTabController;
import gui.popups.WarningPopup;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.TaskTreeItem;

import java.io.*;
import java.time.LocalTime;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * fileAccess method takes path of the file and given time and writes in on a new line in .txt file.
 */
public class FileAccess {

    public FileAccess() {
        // util classes
    }

    public static void saveRecordData() {
        try (FileWriter fw = new FileWriter("records.txt", true)) {

            for (ProjectTreeItem project : ProjectsTabController.getProjects().getJuniors()) {
                for(TaskTreeItem task : project.getJuniors()) {
                    List<String> taskRecords = task.getRecords();

                    if (taskRecords.isEmpty()) continue;

                    taskRecords.forEach(r -> {

                        try {
                            fw.write(project.getValue() + ", " + task.getValue() + ", " + r + "\n");
                        } catch (IOException e) {
                            new WarningPopup("File updating error: " + e);
                        }
                    });
                }
            }



        } catch (IOException e) {
            // TODO is this good enough of a catch?
            new WarningPopup("Could not write to file: " + e);
        }
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
