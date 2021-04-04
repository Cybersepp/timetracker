package data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task implements Data{
    private String taskName;
    private LocalDateTime creationDate;
    private List<String> records;
    private String belongs;

    public Task(String taskName, LocalDateTime creationDate, String belongs) {
        this.taskName = taskName;
        this.creationDate = creationDate;
        this.records = new ArrayList<>();
        this.belongs = belongs;
    }

    public void addRecord(String info) {
        records.add(info);
    }

    public String getName() {
        return this.taskName;
    }

    public String getBelongs() {
        return belongs;
    }

    public List<String> getRecords() {
        return records;
    }
}
