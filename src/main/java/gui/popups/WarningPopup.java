package gui.popups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WarningPopup extends AbstractPopup{

    protected String errorMessage;

    public WarningPopup(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void popup() {

        int minHeight = 100;
        int maxHeight = 100;
        int minWidth = 400;
        int maxWidth = 400;

        Stage window = new Stage();
        window.setTitle("Warning");
        defaultWindowSettings(window, minHeight, minWidth, maxHeight, maxWidth);
        Label label = new Label(errorMessage);
        label.setFont(Font.font ("Verdana", 12));
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> window.close());

        VBox display = new VBox(10);
        display.setPadding(new Insets(10, 40, 30, 40));
        display.setSpacing(10);
        display.getChildren().addAll(label, okButton);
        display.setAlignment(Pos.CENTER);

        Scene scene1= new Scene(display, minWidth, minHeight);
        window.setScene(scene1);
        window.showAndWait();
    }
}
