package gui.icons;

import javafx.scene.image.Image;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class ProjectIcon{

    private CustomImageView icon;
    private final String[] colors = new String[]{"black", "brown", "green", "orange", "red", "violet", "yellow", "blue"};
    private String color;

    public ProjectIcon() {
        int random = ThreadLocalRandom.current().nextInt(0, 8);
        color = colors[random];
        chooseIcon();
    }

    public ProjectIcon(String color) {
        this.color = color;
        chooseIcon();
    }

    public String getCurrentColor() {
        return color;
    }

    public CustomImageView getIcon() {
        return icon;
    }

    private void chooseIcon() {
        for (String col : colors) {
            if (col.equals(color)) {
                icon = new CustomImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon_" + color + ".png"))), this);
                return;
            }
        }
        //if there is a unrecognized color
        color = "blue";
        icon = new CustomImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon_blue.png"))), this);
    }
}
