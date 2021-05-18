package data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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

    public Recording(TaskTreeItem parentTask, int duration) {
        this.parentTask = parentTask;
        this.durationInSec = duration;
        this.recordStart = LocalDateTime.now();
        this.recordEnd = recordStart;
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

    public int getDurationInMinutes() {
        return Math.round(durationInSec / 60);
    }

    public void addToDuration(int seconds) {
        durationInSec += seconds;
    }

    /**
     * This method is used in HistoryTabService.configureColumns, do not delete
     * @return String of duration in time Format
     */
    public String getDurationInString() {
        int hours = durationInSec / 3600;
        int minutes = (durationInSec - hours * 3600) / 60;
        int seconds = durationInSec - hours * 3600 - minutes * 60;
        return timeString(hours) + ":" + timeString(minutes) + ":" + timeString(seconds);
    }

    public String timeString(int time) {
        if (time < 10) {
            return "0"+time;
        }
        return String.valueOf(time);
    }

    private void setDuration() {
        this.durationInSec = (int) ChronoUnit.SECONDS.between(recordStart, recordEnd);
    }

    public TaskTreeItem getParentTask() {
        return parentTask;
    }

    /** Is used for populating HistoryTab.configureColumns (Do not delete) */
    public String getTaskName() {return parentTask.getValue();}

    public ProjectTreeItem getParentProject() {
        return (ProjectTreeItem) parentTask.getParent();
    }

    /** Is used for populating HistoryTab.configureColumns (Do not delete) */
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

    @Override
    public String toString() {
        return getParentProject().getValue() + " " + parentTask.getValue() + "  " + durationInSec;
    }
}
