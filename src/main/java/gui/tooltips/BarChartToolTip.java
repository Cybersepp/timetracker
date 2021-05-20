package gui.tooltips;

import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class BarChartToolTip extends Tooltip {

    public BarChartToolTip(String seconds, String projectName) {
        super();
        tooltipSetup(seconds, projectName);
    }

    private void tooltipSetup(String seconds, String projectName) {
        styleProperty().set("-fx-font: normal bold 12 Langdon; "
                + "-fx-base: black; "
                + "-fx-background-color: rgba(46, 49, 49, 0.8); "
                + "-fx-text-fill: orange; "
                + "-fx-stroke-width: 5");
        setText(projectName + ": " + "\n" + calculateTime(seconds));
        setShowDelay(Duration.ZERO);
    }

    /**
     * Calculates time to show for the graph tooltip.
     * @param duration in seconds.
     * @return a string which shows time.
     */
    private String calculateTime(String duration) {
        var builder = new StringBuilder();
        int seconds = (int) Math.round(Double.parseDouble(duration));
        int minutes = seconds / 60;
        int hours = minutes / 60;

        if (minutes != 0) {
            seconds -= minutes * 60;
            if (hours != 0) {
                minutes -= hours * 60;
                if (hours > 24) {
                    int days = hours / 24;
                    if (isSingular(days)) {
                        builder.append(days).append(" day ");
                    } else {
                        builder.append(days).append(" days ");
                    }
                    hours -= days * 24;
                }
                if (isSingular(hours)) {
                    builder.append(hours).append(" hour ");
                } else {
                    builder.append(hours).append(" hours ");
                }
            }
            if (isSingular(minutes)) {
                builder.append(minutes).append(" minute ");
            } else {
                builder.append(minutes).append(" minutes ");
            }
        }

        if (isSingular(seconds)) {
            builder.append(seconds).append(" second ");
        } else {
            builder.append(seconds).append(" seconds ");
        }
        return builder.toString();
    }

    /**
     * Method to check if there is only one time unit or many.
     * @param time unit.
     * @return true if it is in singular.
     */
    private boolean isSingular(int time) {
        return time == 1;
    }
}
