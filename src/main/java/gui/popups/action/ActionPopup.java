package gui.popups.action;

import gui.controllers.ProjectsTabController;
import gui.popups.AbstractPopup;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import logic.treeItems.ProjectTreeItem;

public abstract class ActionPopup extends AbstractPopup {

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
     * Sets the scene with the default width and height for @ActionPopup.
     * @param stage - the stage where the scene is configured to
     * @param vBox - VBox that contains all the elements that are meant to be on the stage.
     */
    @Override
    protected void setScene(Stage stage, VBox vBox) {
        var scene = new Scene(vBox, 300, 250);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Configures button font and width for @ActionPopup.
     * @param name - the text on the button
     * @return Button with configured settings
     */
    @Override
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
    @Override
    protected Label addLabel(String name) {
        var label = new Label(name);
        label.setFont(Font.font ("Verdana", FontWeight.BOLD, 15));
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        return label;
    }

    /**
     * Method for creating a comboBox with all the existing non-archived projects
     * @param selectedProject - the project that is going to be selected at start
     * @return Configured comboBox that is filled with data
     */
    protected ComboBox<ProjectTreeItem> addProjectComboBox(ProjectTreeItem selectedProject) {
        ComboBox<ProjectTreeItem> projectComboBox = new ComboBox<>();
        ProjectsTabController.getActiveRoot().getJuniors().forEach(projectTreeItem -> projectComboBox.getItems().add(projectTreeItem));
        projectComboBox.getSelectionModel().select(selectedProject);
        projectComboBox.setMaxWidth(Double.MAX_VALUE);
        return projectComboBox;
    }

    /**
     * Method for configuring the default Button's functionality for @ActionPopup.
     * @param button - the default button
     * @param stage - the Stage that everything is happening in
     * @param textField - input field
     */
    protected void mainButtonFunctionality(Button button, Stage stage, TextField textField) {
        mainButtonFunctionality(button, stage);
    }

    /**
     * Method for configuring the default Button's functionality for @ActionPopup.
     * @param button - the default button
     * @param stage - the Stage that everything is happening in
     */
    protected void mainButtonFunctionality(Button button, Stage stage) {
        button.setDefaultButton(true);
        button.setDisable(true);
        button.setStyle("-fx-background-color: #00B5FE");
    }

    /**
     * Listener for setting the maximum length characters typed inside the TextField
     * @param textField - the TextField to be listened to
     */
    protected void textFieldLengthListener(TextField textField, int maxLength){
        if (textField.getText().length() >= maxLength) {
            textField.setText(textField.getText().substring(0, maxLength));
        }
    }
}
