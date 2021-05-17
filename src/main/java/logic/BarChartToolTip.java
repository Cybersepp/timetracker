package logic;

import javafx.scene.control.Tooltip;

public class BarChartToolTip extends Tooltip {

    public BarChartToolTip(String seconds, String projectName) {
        super();
        tooltipSetup(seconds, projectName);
    }

    private void tooltipSetup(String seconds, String projectName) {
        String minutes = String.valueOf(Math.round(Float.parseFloat(seconds)) / 60);
        String hours = String.valueOf(Math.round(Float.parseFloat(minutes)) / 60);
        String text = (projectName + ": "  + "\n" +
                seconds + " seconds" + "\n" +
                minutes + " minutes" + "\n" +
                hours + " hours");
        styleProperty().set("-fx-font: normal bold 12 Langdon; "
                + "-fx-base: black; "
                + "-fx-text-fill: orange;"
                +  "-fx-stroke-width: 5");
        setText(text);
    }
}
