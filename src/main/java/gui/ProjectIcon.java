package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class ProjectIcon {

    private Node icon;
    private final String[] colors = new String[]{"black", "brown", "green", "orange", "red", "violet", "yellow", "blue"};

    public ProjectIcon() {
        int random = ThreadLocalRandom.current().nextInt(0, 8);
        chooseColor(colors[random]);
    }

    public ProjectIcon(String color) {
        chooseColor(color);
    }

    public Node getIcon() {
        return icon;
    }

    private void chooseColor(String color) {
        for (String col : colors) {
            if (col.equals(color)) {
                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_" + color + ".png"))));
                return;
            }
        }
        icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_blue.png"))));

    }
}
