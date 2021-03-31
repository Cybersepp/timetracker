package data;

import com.github.cliftonlabs.json_simple.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Record {
    private LocalDateTime recordStart;
    private LocalDateTime recordEnd;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void setRecordStart() {
        this.recordStart = LocalDateTime.now();
    }

    public void setRecordEnd() {
        this.recordEnd = LocalDateTime.now();
    }

    public String getRecordStart() {
        return this.recordStart.format(formatter);
    }

    public String getRecordEnd() {
        return this.recordEnd.format(formatter);
    }
}
