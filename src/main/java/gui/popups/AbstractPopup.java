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

    /**
     * Makes sure you can't access the stage the popup popped out from and sets the minimum and maximum height and width for the window.
     */
    protected void defaultWindowSettings(Stage window, int minHeight, int minWidth, int maxHeight, int maxWidth) {

        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinHeight(minHeight);
        window.setMinWidth(minWidth);
        window.setMaxHeight(maxHeight);
        window.setMaxWidth(maxWidth);
    }

    /**
     * Makes the Popup window not resizable and also makes sure that you can't use the stage it popped out from.
     */
    protected void defaultWindowSettings(Stage window) {
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
    }

    /**
     * Method for creating a VBox with the default settings and adding all the children to it.
     * @param nodes the children to be added to the VBox
     * @return VBox with all the children and configured settings
     */
    protected VBox addVBox(Node[] nodes) {
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10, 40, 30, 40));
        Arrays.stream(nodes).forEach(node -> vBox.getChildren().add(node));
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    /**
     * Functionality for adding the stage with the needed configurations.
     */
    protected abstract Stage addStage();

    /**
     * Functionality for adding the scene with the needed configurations.
     */
    protected abstract void setScene(Stage stage, VBox vBox);

    /**
     * Functionality for adding the button with the needed configurations.
     */
    protected abstract Button addButton(String name);

    /**
     * Functionality for adding the label with the needed configurations.
     */
    protected abstract Label addLabel(String name);
}
