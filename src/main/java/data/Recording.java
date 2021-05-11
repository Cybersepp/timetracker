package data;

import logic.treeItems.ProjectTreeItem;
import logic.treeItems.TaskTreeItem;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Recording is class which holds a recording's data and also populates history tab's table view.
 */
public class Recording {

    private LocalDateTime recordStart;
    private LocalDateTime recordEnd;
    private int durationInSec;
    private TaskTreeItem parentTask;

    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Recording(TaskTreeItem parentTask) {
        this.parentTask = parentTask;
    }

    public Recording(TaskTreeItem parentTask, int durationInSec) {
        this.durationInSec = durationInSec;
        this.parentTask = parentTask;
        this.recordEnd = LocalDateTime.now();
        this.recordStart = LocalDateTime.now();
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
        try {
            this.durationInSec = (int) ChronoUnit.SECONDS.between(recordStart, recordEnd);
        } catch (NullPointerException noStartingDate) {
            return;
        }
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
        this.parentTask.getRecordings().remove(this);
        parentTask.getRecordings().add(this);
        this.parentTask = parentTask;
    }

    public String getRecordInfo() {
        return getRecordStart() + ", " + getRecordEnd() + ", " + durationInSec;
    }

    @Override
    public String toString() {
        return getParentProject().getValue() + parentTask.getValue() + "  " + recordStart.toString();
    }
}
