package gui.icons;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomImageView extends ImageView {

    private final ProjectIcon icon;

    public CustomImageView(Image image, ProjectIcon icon) {
        super(image);
        this.icon = icon;
    }

    public ProjectIcon getIcon() {
        return icon;
    }
}
