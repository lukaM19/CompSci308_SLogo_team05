package slogo.command.exception.parameterexception.impliedparameterexception;

public class ImpliedParametersNotSetException extends ImpliedParameterException {

  /***
   * Creates an exception related to implied parameters not being set
   *
   * @param errorMessage is the message to show
   */
  public ImpliedParametersNotSetException(String errorMessage) {
    super(errorMessage);
  }
}
