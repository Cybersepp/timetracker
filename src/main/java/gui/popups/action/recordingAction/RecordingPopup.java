package gui.popups.action.recordingAction;

import data.Recording;
import gui.popups.action.ActionPopup;

public abstract class RecordingPopup extends ActionPopup {

    protected final Recording recording;

    protected RecordingPopup(Recording recording) {
        this.recording = recording;
    }


}
