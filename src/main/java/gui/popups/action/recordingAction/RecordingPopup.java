package gui.popups.action.recordingAction;

import data.Recording;
import gui.popups.action.ActionPopup;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public abstract class RecordingPopup extends ActionPopup {

    protected final Recording recording;

    protected RecordingPopup(Recording recording) {
        this.recording = recording;
    }

    /**
     * Method for creating a HBox with default settings
     * @param nodes - the children that are going to be placed inside the HBox
     * @return HBox with default properties and children added to it
     */
    protected HBox addHBox (Node[] nodes) {
        var hBox = new HBox(10);
        hBox.getChildren().addAll(nodes);
        hBox.maxWidth(Double.MAX_VALUE);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    /**
     * Creates the TextField for the TreeItems-s with the lengthProperty listener
     * @return TextField with the length property listener
     */
    protected TextField addTextField() {
        var textField = new TextField();
        textField.lengthProperty().addListener((observable, oldValue, newValue) -> textFieldLengthListener(textField, 8));
        return textField;
    }
}
