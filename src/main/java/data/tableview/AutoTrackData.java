package data.tableview;

import java.time.LocalTime;

public class AutoTrackData {

    private String name;
    private LocalTime duration;
    private String initialDate;

    public AutoTrackData(String name, LocalTime duration) {
        this.name = name;
        this.duration = duration;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    @Override
    public String toString() {
        return name + " " + duration;
    }
}
