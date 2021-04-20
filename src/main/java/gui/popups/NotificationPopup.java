package gui.popups;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public abstract class NotificationPopup extends AbstractPopup{

    protected String errorMessage;

    protected NotificationPopup(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    protected Stage addStage() {
        var stage = new Stage();
        defaultWindowSettings(stage);
        return stage;
    }

    @Override
    protected Button addButton(String name) {
        var button = new Button(name);
        button.setPrefWidth(50);
        return button;
    }

    @Override
    protected Label addLabel(String name) {
        Label label = new Label(name);
        label.setFont(Font.font ("Verdana", 12));
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        return label;
    }

    @Override
    protected void setScene(Stage stage, VBox vBox) {
        Scene scene= new Scene(vBox, 400, 120);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
