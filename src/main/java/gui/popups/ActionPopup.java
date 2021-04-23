package gui.popups;

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

public abstract class ActionPopup extends AbstractPopup{

    protected final AbstractTreeItem treeItem;
    protected final String type;

    protected ActionPopup(AbstractTreeItem treeItem, String type) {
        this.treeItem = treeItem;
        this.type = type;
    }

    @Override
    protected Stage addStage() {
        var stage = new Stage();
        defaultWindowSettings(stage, 250, 300, 375, 450);
        return stage;
    }

    protected TextField addTextField() {
        TextField textField = new TextField();
        textField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            int maxLength = 25;
            if (textField.getText().length() >= maxLength) {
                textField.setText(textField.getText().substring(0, maxLength));
            }
        });
        return textField;
    }

    protected Button addButton(String name) {
        Button button = new Button(name);
        button.setFont(Font.font ("Verdana", 14));
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    protected Label addLabel(String name) {
        Label label = new Label(name);
        label.setFont(Font.font ("Verdana", FontWeight.BOLD, 15));
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        return label;
    }

    protected abstract void mainButtonFunctionality(AbstractTreeItem treeItem, Button button, Stage stage, TextField textField);

    @Override
    protected void setScene(Stage stage, VBox vBox) {
        Scene scene = new Scene(vBox, 300, 250);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
