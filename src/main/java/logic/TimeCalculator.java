package logic;

import java.time.LocalTime;

public final class TimeCalculator {
    private TimeCalculator() {
        // util class
    }
    public static LocalTime returnTime() {
        return java.time.LocalTime.now();

    }
}
