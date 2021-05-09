package gui.popups.action.recordingAction;

import data.Recording;
import javafx.scene.Node;

public class ChangeRecordingParentPopup extends RecordingPopup {

    public ChangeRecordingParentPopup(Recording recording) {
        super(recording);
    }

    @Override
    public void popup() {
        var window = addStage();
        var mainButton = addButton("Change task");
        var cancelButton = addButton("Cancel");
        var label = this.addLabel(recording.getRecordInfo());

        window.setTitle("Change task");

        mainButtonFunctionality(mainButton, window);
        cancelButton.setOnAction(event -> window.close());
        cancelButton.setCancelButton(true);

        var display = addVBox(new Node[]{label, mainButton, cancelButton});

        setScene(window, display);
    }
}
