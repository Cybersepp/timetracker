package gui.icons;

import javafx.scene.image.Image;

import java.util.Objects;

public class TaskIcon{

    private final TaskImageView imageView;

    public TaskImageView getImageView() {
        return imageView;
    }

    public TaskIcon(boolean done) {
        if (done) {
            imageView = new TaskImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon_checkmark.png"))), this);
        }
        else {
            imageView = new TaskImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon_line.png"))), this);
        }
    }

    /**
     * Changes imageView Image according to task isDone state
     * @param done - task isDone value
     */
    public void setIcon(boolean done) {
        if (done) {
            imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon_checkmark.png"))));
        }
        else {
            imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon_line.png"))));
        }
    }
}
