package gui.icons;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class TaskIcon {

    private Node icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon_line.png"))));

    public Node getIcon() {
        return icon;
    }
}
