package slogo.command.math;

import java.util.List;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterException;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;

public abstract class Operation extends Math{
  public static int OPERATION_PARAM_NUMBER = 2;
  public static int FIRST_PARAM_INDEX = 0;
  public static int SECOND_PARAM_INDEX = 1;

  protected double param1;
  protected double param2;

  /***
   * Creates a Math Command that takes two parameters
   *
   * @param parameters - parameters for command
   */
  public Operation(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Makes params private instance variables
   *
   * @throws WrongParameterTypeException if wrong parameter type passed
   * @throws WrongParameterNumberException if wrong number of parameters passed
   */
  @Override
  protected void setUpExecution()
      throws WrongParameterTypeException, WrongParameterNumberException, ImpliedParameterException {
    super.setUpExecution();
    checkForExactParameterLength(OPERATION_PARAM_NUMBER);
    param1 = getMathParam(FIRST_PARAM_INDEX);
    param2 = getMathParam(SECOND_PARAM_INDEX);
  }
}
