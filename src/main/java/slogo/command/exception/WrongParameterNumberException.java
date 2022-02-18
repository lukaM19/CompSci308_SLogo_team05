package slogo.command.exception;

public class WrongParameterNumberException extends CommandException {

  /***
   * Creates an exception related to a Command getting the wrong number of parameters
   *
   * @param errorMessage is the message to show
   */
  public WrongParameterNumberException(String errorMessage) {
    super(errorMessage);
  }
}
