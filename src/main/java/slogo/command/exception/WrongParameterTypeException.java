package slogo.command.exception;

public class WrongParameterTypeException extends CommandException {

  /***
   * Creates an exception related to a Command getting parameters of the wrong type
   *
   * @param errorMessage is the message to show
   */
  public WrongParameterTypeException(String errorMessage) {
    super(errorMessage);
  }
}
