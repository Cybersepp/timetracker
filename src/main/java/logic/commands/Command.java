package logic.commands;

public interface Command {

    /**
     * calls the function of the Command
     */
    void command();

    /**
     * Checks if the function can be called safely
     */
    void commandControl();
}
