package logic.Timer;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Timer {
    private final Label timerLabel;
    private Timeline timeline;
    private LocalTime time = LocalTime.parse("00:00:00");
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Timer(Label label) {
        this.timerLabel = label;
    }

    public void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> incrementTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void incrementTime() {
        time = time.plusSeconds(1);
        timerLabel.setText(time.format(dtf));
    }

    public void endTimer() {
        timeline.stop();
        time = LocalTime.parse("00:00:00");
        timerLabel.setText(time.format(dtf));
    }
}
