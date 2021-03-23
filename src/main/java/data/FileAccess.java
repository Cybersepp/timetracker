package data;

import java.io.*;
import java.time.LocalTime;

/**
 * fileAccess method takes path of the file and given time and writes in on a new line in .csv file.
 */
public class FileAccess {
    private final File fileName = new File("records.csv");

    public void addRecordToFile(LocalTime time) throws IOException {
        try (var writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(time + "\n");
        }
    }

    /**
     * howManyRecords method calculates how many entries are in a given file.
     * @returns an integer which indicates how many entries there are in the file.
     * @throws IOException
     */
    public int howManyRecords() throws IOException {
        int counter = 0;
        try (var reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) {
                counter++;
            }
        }
        return counter;
    }
}
