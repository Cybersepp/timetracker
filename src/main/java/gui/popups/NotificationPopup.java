package gui.popups;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public abstract class NotificationPopup extends AbstractPopup{

    protected String errorMessage;

    protected NotificationPopup(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    protected Stage addStage() {
        Stage stage = new Stage();
        defaultWindowSettings(stage);
        return stage;
    }

    @Override
    protected void setScene(Stage stage, VBox vBox) {
        Scene scene= new Scene(vBox, 400, 100);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @Override
    protected Button addButton(String name) {
        return new Button(name);
    }

    @Override
    protected Label addLabel(String name) {
        Label label = new Label(name);
        label.setFont(Font.font ("Verdana", 12));
        return label;
    }
}
