package logic;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * This class is made for the project treeView.
 * Due to the fact it wouldn't pass the tests and threw a lot of errors this is not implemented at the moment.
 * This planned to be implemented later in the following way TreeItem<CustomHBox>.
 */
public class CustomHBox extends HBox {

    private Label treeText;
    private Button treeButton;

    public CustomHBox(Label treeText, Button treeButton) {
        super();
        this.treeText = treeText;
        this.treeButton = treeButton;

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        this.getChildren().addAll(treeText, region, treeButton);
    }

    public CustomHBox(Label treeText) {
        super();
        this.treeText = treeText;

        this.getChildren().add(treeText);
        this.setAlignment(Pos.BASELINE_LEFT);
    }

    public String getName() {
        return treeText.getText();
    }

    public void setName(String treeText) {
        this.treeText.setText(treeText);
    }

    public String getButtonValue() {
        return treeButton.getText();
    }
}
