package gui.icons;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * ImageView class for projects
 */
public class ProjectImageView extends ImageView {

    private final ProjectIcon icon;

    public ProjectImageView(Image image, ProjectIcon icon) {
        super(image);
        this.icon = icon;
    }

    public ProjectIcon getIcon() {
        return icon;
    }
}
