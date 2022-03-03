package slogo.command.exception;

public class CommandNotRunException extends CommandException {

  /***
   * Creates an exception related to Command not being run
   *
   * @param errorMessage is the message to show
   */
  public CommandNotRunException(String errorMessage) {
    super(errorMessage);
  }
}
