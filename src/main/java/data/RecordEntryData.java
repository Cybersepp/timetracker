package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RecordEntryData {

    private String projectName;
    private String taskName;
    private String start;
    private Integer durationInSec;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public RecordEntryData(String projectName, String taskName, String start, String durationInSec) {
        this.projectName = projectName;
        this.taskName = taskName;
        this.start = start;
        this.durationInSec = Integer.parseInt(durationInSec);
    }

    public String getProjectName() {
        return projectName;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getStart() {
        return start;
    }

    public Integer getDurationInSec() {
        return durationInSec;
    }

    public Date getDate() throws ParseException {
        return sdf.parse(start.split(" ")[0]);
    }

    @Override
    public String toString() {
        return projectName + taskName + "  " + start;
    }

}
