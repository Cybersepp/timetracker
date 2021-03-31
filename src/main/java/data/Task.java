package data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task implements Data{
    private String taskName;
    private LocalDateTime creationDate;
    private List<Record> records;
    private String belongs;

    public Task(String taskName, LocalDateTime creationDate, String belongs) {
        this.taskName = taskName;
        this.creationDate = creationDate;
        this.records = new ArrayList<>();
    }

    public void addRecord(Record record) {
        this.records.add(record);
    }

    public String getName() {
        return this.taskName;
    }

    public String getBelongs() {
        return belongs;
    }
}
