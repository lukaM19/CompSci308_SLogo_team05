package slogo.command.exception.parameterexception.impliedparameterexception;

import slogo.command.exception.parameterexception.ParameterException;

public abstract class ImpliedParameterException extends ParameterException {

  /***
   * Creates an exception related to implied parameters passed to command
   *
   * @param errorMessage is the message to show
   */
  public ImpliedParameterException(String errorMessage) {
    super(errorMessage);
  }
}
