package gui.popups.action.recordingAction;

import data.Recording;
import gui.popups.action.ActionPopup;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public abstract class RecordingPopup extends ActionPopup {

    protected final Recording recording;

    protected RecordingPopup(Recording recording) {
        this.recording = recording;
    }

    protected HBox addHBox (Node[] nodes) {
        var hBox = new HBox(10);
        hBox.getChildren().addAll(nodes);
        hBox.maxWidth(Double.MAX_VALUE);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }
}
