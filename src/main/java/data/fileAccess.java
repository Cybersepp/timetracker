package data;

import java.io.*;
import java.time.LocalTime;

/**
 * fileAccess method takes path of the file and given time and writes in on a new line in .csv file.
 */
public class fileAccess {
    public void addRecordToFile(File path, LocalTime time) throws IOException {
        try (var writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(time + "\n");
        }
    }

    /**
     * howManyRecords method calculates how many entries are in a given file.
     * @param path is the path for the file.
     * @returns an integer which indicates how many entries there are in the file.
     * @throws IOException
     */
    public int howManyRecords(File path) throws IOException {
        int counter = 0;
        try (var reader = new BufferedReader(new FileReader(path))) {
            while (reader.readLine() != null) {
                counter++;
            }
        }
        return counter;
    }
}
