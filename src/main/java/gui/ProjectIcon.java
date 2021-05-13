package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class ProjectIcon {

    private Node icon;

    public ProjectIcon() {
        int random = ThreadLocalRandom.current().nextInt(0, 8);
        chooseColor(random);
    }

    public ProjectIcon(String color) {
        chooseColor(color);
    }

    public Node getIcon() {
        return icon;
    }

    private void chooseColor(int color) {

        switch (color) {
            case 0:
                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_black.png"))));
                break;
            case 1:
                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_brown.png"))));
                break;
            case 2:
                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_green.png"))));
                break;
            case 3:
                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_orange.png"))));
                break;
            case 4:
                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_red.png"))));
                break;
            case 5:
                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_violet.png"))));
                break;
            case 6:
                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_yellow.png"))));
                break;
            default:
                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_blue.png"))));
                break;
        }
    }

    private void chooseColor(String color) {
        switch (color) {
            case "black":
                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_black.png"))));
                break;
            case "brown":
                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_brown.png"))));
                break;
            case "green":
                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_green.png"))));
                break;
            case "orange":
                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_orange.png"))));
                break;
            case "red":
                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_red.png"))));
                break;
            case "violet":
                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_violet.png"))));
                break;
            case "yellow":
                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_yellow.png"))));
                break;
            default:
                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("colors/icon_blue.png"))));
                break;
        }
    }
}
