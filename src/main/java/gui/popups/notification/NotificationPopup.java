package gui.popups.notification;

import gui.popups.AbstractPopup;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public abstract class NotificationPopup extends AbstractPopup {

    protected String errorMessage;

    protected NotificationPopup(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Stage configuration for @NotificationPopup
     * @return configured stage
     */
    @Override
    protected Stage addStage() {
        var stage = new Stage();
        defaultWindowSettings(stage, false);
        return stage;
    }

    /**
     * Configures button font and width for @NotificationPopup.
     * @param name the text on the button
     * @return Button with configured settings
     */
    @Override
    protected Button addButton(String name) {
        var button = new Button(name);
        button.setFont(Font.font ("Verdana", 13));
        button.setPrefWidth(50);
        return button;
    }

    /**
     * Sets the labels alignment, text properties and font for @NotificationPopup.
     * @param name label's text
     * @return Label with configured settings
     */
    @Override
    protected Label addLabel(String name) {
        Label label = new Label(name);
        label.setFont(Font.font ("Verdana", 12));
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        return label;
    }

    /**
     * Sets the scene with the default width and height for @NotificationPopup.
     * @param stage the stage where the scene is configured to
     * @param vBox VBox that contains all the elements that are meant to be on the stage.
     */
    @Override
    protected void setScene(Stage stage, VBox vBox) {
        Scene scene= new Scene(vBox, 400, 120);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
