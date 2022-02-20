package slogo.command.exception.parameterexception;

import slogo.command.exception.parameterexception.ParameterException;

public class WrongParameterNumberException extends ParameterException {

  /***
   * Creates an exception related to a Command getting the wrong number of parameters
   *
   * @param errorMessage is the message to show
   */
  public WrongParameterNumberException(String errorMessage) {
    super(errorMessage);
  }
}
