package slogo.command.exception.parameterexception;

public class ParameterNotFoundException extends ParameterException {

  /***
   * Creates an exception related to a Command not receiving a needed parameter
   *
   * @param errorMessage is the message to show
   */
  public ParameterNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
