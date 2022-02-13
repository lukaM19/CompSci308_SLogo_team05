package slogo.Parser

/**
 * The Parser interface will handle each command entered into the command line, such as Move or Math operations.
 * The interface should only have one API for the controller to call when a new command line has
 * been entered. The Parse command should be able to field any type of command line entry and create the appropriate
 * Command object (Math, Move, etc.)
 */
public interface Parser {
    /**
     * The parse method will be called by the controller in order to receieve the String entered in the command
     * line. This method will throw exceptions if the String entered has a Syntax error.
     * The Command returned will be passed from the controller to the model, which will calculate the resulting
     * states. The parse command itself will have helper methods that create the Commands or deal with throwing
     * exceptions.
     */

    public Command parse();
}