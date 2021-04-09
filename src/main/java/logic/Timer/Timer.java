package logic.Timer;

import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Label;
import javafx.util.Duration;


public class Timer {

    private Timeline timeline;
    private final Label timerLabel = new Label();
    private final Label splitTimerLabel = new Label();
    private final DoubleProperty timeSeconds = new SimpleDoubleProperty();
    private final DoubleProperty splitTimeSeconds = new SimpleDoubleProperty();
    private final Duration time = Duration.ZERO;
    private final Duration splitTime = Duration.ZERO;


}
