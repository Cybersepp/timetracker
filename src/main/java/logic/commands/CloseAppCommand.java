package logic.commands;

import data.FileAccess;
import gui.popups.notification.WarningPopup;
import javafx.application.Platform;

public class CloseAppCommand implements Command{
    @Override
    public void command() {
        FileAccess.saveData();
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void commandControl() {
        var warningPopup = new WarningPopup("Are you sure you wish to close the app? (You will lose any active recording and all tracked data)", this);
        warningPopup.popup();
    }
}
