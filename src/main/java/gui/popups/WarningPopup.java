package gui.popups;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.commands.Command;

public class WarningPopup extends NotificationPopup{

    private final Command command;

    public WarningPopup(String errorMessage, Command command) {
        super(errorMessage);
        this.command = command;
    }

    @Override
    public void popup() {

        Stage window = new Stage();
        window.setTitle("Warning");

        Label label = addLabel(errorMessage);
        Button yesButton = addButton("Yes");
        yesButton.setOnAction(e -> yesFunction(window));
        Button noButton = addButton("No");
        noButton.setOnAction(e -> window.close());

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(yesButton, noButton);

        VBox vBox = addVBox(new Node[]{label, hBox});

        setScene(window, vBox);
    }

    private void yesFunction(Stage stage) {
        command.command();
        stage.close();
    }
}
