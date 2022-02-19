package slogo.command.exception.parameterexception.impliedparameterexception;

import slogo.command.exception.parameterexception.ParameterException;

public abstract class WrongImpliedParameterTypeException extends ImpliedParameterException {

  /***
   * Creates an exception related to wrong implied parameter type
   *
   * @param errorMessage is the message to show
   */
  public WrongImpliedParameterTypeException(String errorMessage) {
    super(errorMessage);
  }
}
