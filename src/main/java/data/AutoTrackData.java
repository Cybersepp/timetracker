package data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AutoTrackData {

    private String name;
    private LocalTime duration;
    private String initialDate;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public AutoTrackData(String name, LocalTime duration) {
        this.name = name;
        this.duration = duration;
        this.initialDate = LocalDateTime.now().format(formatter);

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

    public int calculateDuration() {
        int seconds = duration.getSecond();
        int minutes = duration.getMinute();
        int hours = duration.getHour();
        return seconds + minutes * 60 + hours * 60 * 60;
    }
}
