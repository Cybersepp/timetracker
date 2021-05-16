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

    // Constructor for when reading a color from file (is not implemented this way atm)
    public ProjectIcon(String color) {
        this.color = color;
        imageView = new ProjectImageView(chooseImage(), this);
    }

    // Method for getting the color to write to file (is not implemented this way atm)
    public String getCurrentColor() {
        return color;
    }

    public ProjectImageView getImageView() {
        return imageView;
    }

    /**
     * Method that is meant for using when reading the color from a file (don't know if we want to set a certain color yet or let it be random at all times)
     * @return icon with a certain color for the project
     */
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

    /**
     * Method that changes the color of the project icon to be the same as ProjectIcon color field
     */
    private void setColor() {
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon_" + color + ".png"))));
    }

    /**
     * Method to change ProjectTreeItems icon colors
     */
    public void nextColor() {
        colorLocation++;
        if (colorLocation >= colors.length) {
            colorLocation = 0;
        }
        color = colors[colorLocation];
        setColor();
    }
}
