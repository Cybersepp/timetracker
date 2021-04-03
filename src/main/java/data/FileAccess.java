package data;

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
            List<Task> taskList = DataHandler.getTaskList();

            for (Task task : taskList) {
                List<String> taskRecords = task.getRecords();

                if (taskRecords.isEmpty()) continue;

                taskRecords.forEach(r -> {
                    try {
                        fw.write(task.getBelongs() + ", " + task.getName() + ", " + r + "\n");
                    } catch (IOException ignored) { }
                });

            }

        } catch (IOException e) {
            System.out.println("File updating error.");
        }
    }

    public static Map<String, Float> getProjectData() throws IOException {

        // TODO divison by 60 is quite ugly, I think we should change it somewhere to hours.
        String strCurrentLine;
        Map<String, Float> projects = new HashMap<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader("records.txt"))) {
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
