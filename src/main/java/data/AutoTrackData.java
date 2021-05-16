package data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class AutoTrackData {

    private String name;
    private LocalTime duration;
    private LocalDateTime endTime;
    private LocalDateTime startTime;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public AutoTrackData(String name, LocalTime duration) {
        this.name = name;
        this.duration = duration;
        this.endTime = LocalDateTime.now();
        this.startTime = calculateStartTime();
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

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return name + " " + duration;
    }

    public LocalDateTime calculateStartTime() {
        startTime = endTime.minusSeconds(duration.getSecond());
        startTime = endTime.minusMinutes(duration.getMinute());
        startTime = endTime.minusHours(duration.getHour());
        return startTime;
    }

    public int calculateDurationInSec() {
        return  duration.getSecond() + duration.getMinute() * 60 + duration.getHour() * 60 * 60;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
