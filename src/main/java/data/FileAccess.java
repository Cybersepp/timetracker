package data;

import java.io.*;
import java.time.LocalTime;
import java.io.FileWriter;
import java.util.List;

/**
 * fileAccess method takes path of the file and given time and writes in on a new line in .csv file.
 */
public class FileAccess {

    public static void saveData() {
        try (FileWriter fw = new FileWriter("records.txt", false)) {
            List<Task> taskList = DataHandler.getTaskList();

            for (Task task : taskList) {
                List<String> taskRecords = task.getRecords();

                if (taskRecords.isEmpty()) continue;

                taskRecords.forEach(r -> {
                    try {
                        fw.write(task.getBelongs() + ", " + task.getName() + ": " + r + "\n");
                    } catch (IOException ignored) { }
                });

            }

        } catch (IOException e) {
            System.out.println("File updating error.");
        }
    }

}
