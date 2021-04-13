package gui.popups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logic.Treeitems.AbstractTreeItem;

public abstract class ActionPopup extends AbstractPopup{

    protected final AbstractTreeItem treeItem;
    protected final String type;

    public ActionPopup(AbstractTreeItem treeItem, String type) {
        this.treeItem = treeItem;
        this.type = type;
    }

    protected Button addButton(String name) {
        Button button = new Button(name);
        button.setFont(Font.font ("Verdana", 14));
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    protected Label addLabel(String name) {
        Label label = new Label(name);
        label.setFont(Font.font ("Verdana", FontWeight.BOLD, 16));
        return label;
    }

    protected VBox addVBox() {
        VBox display = new VBox(10);
        display.setPadding(new Insets(10, 40, 30, 40));
        display.setAlignment(Pos.CENTER);
        return display;
    }

    protected abstract void mainButtonFunctionality(AbstractTreeItem treeItem, Button button, Stage stage, TextField textField);

}
