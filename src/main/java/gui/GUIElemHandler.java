package gui;

import javafx.scene.control.TextInputDialog;

// I thought it would be a decent idea to dedicate a class to create and handle
// such GUI elements which cannot be or problematically added by SceneBuilder.

// I later plan on creating a whole parent class GUIHandler and child classes for various GUI elements
// but most probably after the alpha checkpoint.
public class GUIElemHandler {


    // Some default values
    private String header = "Header";
    private String title = "Title";
    private String text = "Write here...";


    /**
     *
     * @param text String text to be displayed in the TextField
     * @param header String text to be shown above the TextField
     * @return String the user wrote.
     *
     * This method creates and immediately shows a dialogue window to a user.
     * As no need has arisen yet, it simply gets the user input as the class's main purpose.
     */
    public static String textDialog(String title, String text, String header) {
        TextInputDialog td = new TextInputDialog();

        td.setHeaderText(header);
        td.setTitle(title);
        td.getEditor().setText(text);
        td.setGraphic(null);

        td.showAndWait();

        return td.getEditor().getText();



    }
}
