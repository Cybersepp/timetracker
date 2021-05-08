package gui.popups.action;

import gui.popups.AbstractPopup;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import logic.treeItems.AbstractTreeItem;

public abstract class ActionPopup extends AbstractPopup {

    protected final AbstractTreeItem treeItem;
    protected final String type;

    protected ActionPopup(AbstractTreeItem treeItem, String type) {
        this.treeItem = treeItem;
        this.type = type;
    }

    /**
     * Stage configuration for @ActionPopup-s.
     * @return configured stage
     */
    @Override
    protected Stage addStage() {
        var stage = new Stage();
        defaultWindowSettings(stage, 250, 300, 375, 450);
        return stage;
    }

    /**
     * Creates the TextField for the ActionPopup-s with the lengthProperty listener
     * @return TextField with the length property listener
     */
    protected TextField addTextField() {
        var textField = new TextField();
        textField.lengthProperty().addListener((observable, oldValue, newValue) -> textFieldLengthListener(textField));
        return textField;
    }

    /**
     * Configures button font and width for @ActionPopup.
     * @param name the text on the button
     * @return Button with configured settings
     */
    protected Button addButton(String name) {
        var button = new Button(name);
        button.setFont(Font.font ("Verdana", 14));
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    /**
     * Sets the labels alignment, text properties and font for @ActionPopup.
     * @param name label's text
     * @return Label with configured settings
     */
    protected Label addLabel(String name) {
        var label = new Label(name);
        label.setFont(Font.font ("Verdana", FontWeight.BOLD, 15));
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        return label;
    }

    /**
     * Method for configuring the default Button's functionality for @ActionPopup.
     * @param button the default button
     * @param stage the Stage that everything is happening in
     * @param textField input field
     */
    protected void mainButtonFunctionality(Button button, Stage stage, TextField textField) {
        mainButtonFunctionality(button, stage);
    }

    /**
     * Method for configuring the default Button's functionality for @ActionPopup.
     * @param button the default button
     * @param stage the Stage that everything is happening in
     */
    protected void mainButtonFunctionality(Button button, Stage stage) {
        button.setDefaultButton(true);
        button.setDisable(true);
        button.setStyle("-fx-background-color: #00B5FE");
    }

    /**
     * Sets the scene with the default width and height for @ActionPopup.
     * @param stage the stage where the scene is configured to
     * @param vBox VBox that contains all the elements that are meant to be on the stage.
     */
    @Override
    protected void setScene(Stage stage, VBox vBox) {
        var scene = new Scene(vBox, 300, 250);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Method to sort items into correct order after an action
     * @param abstractTreeItem - the treeItem what will call out its organizeView method
     */
    protected void sortItems(AbstractTreeItem abstractTreeItem) {
        abstractTreeItem.organizeView();
    }

    /**
     * Listener for setting the maximum length of character to project and task values
     * @param textField - the TextField to be listened to
     */
    private void textFieldLengthListener(TextField textField){
        int maxLength = 25;
        if (textField.getText().length() >= maxLength) {
            textField.setText(textField.getText().substring(0, maxLength));
        }
    }
}
