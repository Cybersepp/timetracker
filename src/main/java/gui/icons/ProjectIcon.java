package gui.icons;

import javafx.scene.image.Image;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class ProjectIcon{

    private final ProjectImageView imageView;
    private final String[] colors = new String[]{"black", "brown", "green", "orange", "red", "violet", "yellow", "blue"};
    private String color;
    private int colorLocation;

    public ProjectIcon() {
        int random = ThreadLocalRandom.current().nextInt(0, 8);
        color = colors[random];
        colorLocation = random;
        imageView = new ProjectImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon_" + color + ".png"))), this);
    }

    public ProjectIcon(String color) {
        this.color = color;
        imageView = new ProjectImageView(chooseImage(), this);
    }

    public String getCurrentColor() {
        return color;
    }

    public ProjectImageView getImageView() {
        return imageView;
    }

    private Image chooseImage() {
        for (int i = 0; i < colors.length; i++) {
            if (colors[i].equals(color)) {
                colorLocation = i;
                return new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon_" + color + ".png")));
            }
        }
        //if there is a unrecognized color
        color = "blue";
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon_blue.png")));
    }

    private void setColor() {
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon_" + color + ".png"))));
    }

    public void nextColor() {
        colorLocation++;
        if (colorLocation >= colors.length) {
            colorLocation = 0;
        }
        color = colors[colorLocation];
        setColor();
    }
}
