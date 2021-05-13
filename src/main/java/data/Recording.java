package data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import data.deserialization.ProjectItemDeserialization;
import data.deserialization.RecordingsDeserialization;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.TaskTreeItem;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Recording is class which holds a recording's data and also populates history tab's table view.
 */
@JsonIncludeProperties({"start", "end", "duration"})
@JsonDeserialize(using = RecordingsDeserialization.class)
public class Recording implements Serializable {

    @JsonProperty(value = "start")
    private LocalDateTime recordStart;

    @JsonProperty(value = "end")
    private LocalDateTime recordEnd;

    @JsonProperty(value = "duration")
    private int durationInSec;

    @JsonIgnore
    private TaskTreeItem parentTask;
    @JsonIgnore
    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ----------------- CONSTRUCTORS ---------------------------

    public Recording( TaskTreeItem parentTask, String recordStart, String recordEnd, int durationInSec) {
        this.recordStart = LocalDateTime.parse(recordStart, dateTimeFormat);
        this.recordEnd = LocalDateTime.parse(recordEnd, dateTimeFormat);
        this.durationInSec = durationInSec;
        this.parentTask = parentTask;
    }

    public Recording(TaskTreeItem parentTask) {
        this.parentTask = parentTask;
    }

    public Recording(TaskTreeItem parentTask, LocalDateTime recordStart, LocalDateTime recordEnd) {
        this.parentTask = parentTask;
        this.recordStart = recordStart;
        this.recordEnd = recordEnd;
    }

    public Recording(LocalDateTime start, LocalDateTime end, int duration) {
        this.recordStart = start;
        this.recordEnd = end;
        this.durationInSec = duration;
    }

    public String getRecordStart() {
        return recordStart.format(dateTimeFormat);
    }

    public LocalDateTime getRecordStartInLocalDateTime() {
        return recordStart;
    }

    public void setRecordStart() {
        this.recordStart = LocalDateTime.now();
    }

    public void setRecordStart(LocalDateTime recordStart) {
        this.recordStart = recordStart;
        if (recordEnd != null) {
            setDuration();
        }
    }

    public void setRecordStart(String recordStart) {
        this.recordStart = LocalDateTime.parse(recordStart, dateTimeFormat);
    }

    public String getRecordEnd() {
        return recordEnd.format(dateTimeFormat);
    }

    public LocalDateTime getRecordEndInLocalDateTime() {
        return recordEnd;
    }

    public void setRecordEnd() {
        this.recordEnd = LocalDateTime.now();
        setDuration();
    }

    public void setRecordEnd(LocalDateTime recordEnd) {
        this.recordEnd = recordEnd;
        setDuration();
    }

    public void setRecordEnd(String recordEnd) {
        this.recordEnd = LocalDateTime.parse(recordEnd, dateTimeFormat);
        setDuration();
    }

    public int getDurationInSec() {
        return durationInSec;
    }

    private void setDuration() {
        this.durationInSec = (int) ChronoUnit.SECONDS.between(recordStart, recordEnd);
    }

    public TaskTreeItem getParentTask() {
        return parentTask;
    }

    /** Is used for populating HistoryTab (Do not delete) */
    public String getTaskName() {return parentTask.getValue();}

    public ProjectTreeItem getParentProject() {
        return (ProjectTreeItem) parentTask.getParent();
    }

    /** Is used for populating HistoryTab (Do not delete) */
    public String getProjectName() {return getParentProject().getValue();}

    public void setParentTask(TaskTreeItem parentTask) {
        try {
            this.parentTask.getRecordings().remove(this);
            parentTask.getRecordings().add(this);
            this.parentTask = parentTask;
        } catch (NullPointerException ignored) {
            this.parentTask = parentTask;
        }
    }

    public String getRecordInfo() {
        return getRecordStart() + ", " + getRecordEnd() + ", " + durationInSec;
    }

    @Override
    public String toString() {
        return getParentProject().getValue() + " " + parentTask.getValue() + "  " + recordStart.toString();
    }
}
