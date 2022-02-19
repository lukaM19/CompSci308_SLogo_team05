package slogo.command.exception.parameterexception.impliedparameterexception;

public class ImpliedParameterNotFoundException extends ImpliedParameterException {

  /***
   * Creates an exception related to implied parameters not being found in the map
   *
   * @param errorMessage is the message to show
   */
  public ImpliedParameterNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
