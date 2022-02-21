package slogo.command.exception.parameterexception;

public class UserVarMapNotFoundException extends ParameterException {

  /***
   * Creates an exception related to a Command not receiving the user var map when needed
   *
   * @param errorMessage is the message to show
   */
  public UserVarMapNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
