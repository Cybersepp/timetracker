package gui.popups;

import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class AbstractPopup implements Popup{

    protected void defaultWindowSettings(Stage window, int minHeight, int minWidth, int maxHeight, int maxWidth) {

        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinHeight(minHeight);
        window.setMinWidth(minWidth);
        window.setMaxHeight(maxHeight);
        window.setMaxWidth(maxWidth);
    }
}
