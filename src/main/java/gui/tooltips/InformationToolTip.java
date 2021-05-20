package gui.tooltips;

import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class InformationToolTip extends Tooltip {
    public InformationToolTip(String information) {
        super();
        tooltipSetup(information);
    }

    private void tooltipSetup(String information) {
        styleProperty().set("-fx-font: normal bold 12 Langdon; "
                + "-fx-background-color: rgba(46, 49, 49, 0.8); "
                + "-fx-text-fill: white; "
                + "-fx-stroke-width: 5; "
                + "-fx-padding: 10px 10px 10px 10px;");
        setText(information);
        setShowDelay(Duration.ZERO);
        setShowDuration(Duration.INDEFINITE);
    }
}
