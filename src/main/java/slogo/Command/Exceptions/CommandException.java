package slogo.Command.Exceptions;

public abstract class CommandException extends Exception {

  /***
   * Creates an exception related to Commands
   *
   * @param errorMessage is the message to show
   */
  public CommandException(String errorMessage) {
    super(errorMessage);
  }
}
