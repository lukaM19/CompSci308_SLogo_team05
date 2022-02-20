package slogo.command.exception.parameterexception;

import slogo.command.exception.parameterexception.ParameterException;

public class WrongParameterTypeException extends ParameterException {

  /***
   * Creates an exception related to a Command getting parameters of the wrong type
   *
   * @param errorMessage is the message to show
   */
  public WrongParameterTypeException(String errorMessage) {
    super(errorMessage);
  }
}
