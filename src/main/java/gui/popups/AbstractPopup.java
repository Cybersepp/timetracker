package gui.popups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Arrays;

public abstract class AbstractPopup implements Popup{

    protected void defaultWindowSettings(Stage window, int minHeight, int minWidth, int maxHeight, int maxWidth) {

        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinHeight(minHeight);
        window.setMinWidth(minWidth);
        window.setMaxHeight(maxHeight);
        window.setMaxWidth(maxWidth);
    }

    protected void defaultWindowSettings(Stage window) {

        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
    }

    protected VBox addVBox(Node[] nodes) {
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10, 40, 30, 40));
        Arrays.stream(nodes).forEach(node -> vBox.getChildren().add(node));
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    protected abstract Stage addStage();

    protected abstract void setScene(Stage stage, VBox vBox);

    protected abstract Button addButton(String name);

    protected abstract Label addLabel(String name);
}
