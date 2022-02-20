package slogo.command.exception.parameterexception;

import slogo.command.exception.CommandException;

public abstract class ParameterException extends CommandException {

  /***
   * Creates an exception related to parameters passed to command
   *
   * @param errorMessage is the message to show
   */
  public ParameterException(String errorMessage) {
    super(errorMessage);
  }
}
