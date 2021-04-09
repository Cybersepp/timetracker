package data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Record {
    private LocalDateTime recordStart;
    private LocalDateTime recordEnd;
    private String durationInSec;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void setRecordStart() {
        this.recordStart = LocalDateTime.now();
    }

    public void setRecordEnd() {
        this.recordEnd = LocalDateTime.now();
        setDuration();
    }

    private void setDuration() {
        this.durationInSec = String.valueOf(ChronoUnit.SECONDS.between(recordStart, recordEnd));

    }

    public String getRecordStart() {
        return recordStart.format(formatter);
    }

    public String getRecordEnd() {
        return recordEnd.format(formatter);
    }

    public String getDurationInSec() { return durationInSec;}

    public String getRecordInfo() {
        return getRecordStart() + ", " + getRecordEnd() + ", " + durationInSec;
    }
}
