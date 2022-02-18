package slogo.command.math;

import java.util.List;
import java.util.Map;
import slogo.command.general.Command;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.model.World;

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
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Operation(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {

    super(parameters);
    checkForExactParameterLength(OPERATION_PARAM_NUMBER);
  }

  /***
   * Makes params private instance variables
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws WrongParameterTypeException if wrong parameter type passed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Object> userVars)
      throws WrongParameterTypeException {
    super.setUpExecution(world, userVars);
    param1 = getMathParam(FIRST_PARAM_INDEX);
    param2 = getMathParam(SECOND_PARAM_INDEX);
  }
}
