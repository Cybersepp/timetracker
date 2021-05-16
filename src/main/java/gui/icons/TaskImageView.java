package gui.icons;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * ImageView class for tasks
 */
public class TaskImageView extends ImageView {

    private final TaskIcon icon;

    public TaskImageView(Image image, TaskIcon icon) {
        super(image);
        this.icon = icon;
    }

    public TaskIcon getIcon() {
        return icon;
    }
}
