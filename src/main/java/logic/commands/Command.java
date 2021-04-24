package logic.commands;

public interface Command {

    /**
     * calls the function of the Command
     */
    void command();

    /**
     * calls a popup with the selected Command functionality
     */
    void commandControl();
}
