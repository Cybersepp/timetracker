package gui.popups;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ErrorPopup extends NotificationPopup{

    public ErrorPopup(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public void popup() {

        Stage window = new Stage();
        window.setTitle("Error");

        Label label = addLabel(errorMessage);
        Button okButton = addButton("OK");
        okButton.setOnAction(e -> window.close());

        VBox display = addVBox(new Node[]{label, okButton});

        setScene(window, display);
    }
}
