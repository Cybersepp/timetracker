package gui.popups;

import javafx.geometry.Insets;
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

    /**
     * Pops up a warning message with a specific stage
     */
    @Override
    public void popup() {

        Stage window = addStage();
        window.setTitle("Warning");

        Label label = addLabel(errorMessage);
        Button yesButton = addButton("Yes");
        yesButton.setOnAction(e -> yesFunction(window));
        Button noButton = addButton("No");
        noButton.setOnAction(e -> window.close());
        noButton.setCancelButton(true);

        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(0, 40, 0, 100));
        hBox.getChildren().addAll(yesButton, noButton);

        VBox vBox = addVBox(new Node[]{label, hBox});

        setScene(window, vBox);
    }

    /**
     * Gives the "Yes" button the given functionality with the given @Command
     * @param stage the stage of the warning popup, that is about to be closed
     */
    private void yesFunction(Stage stage) {
        this.command.command();
        stage.close();
    }
}
