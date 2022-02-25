package slogo.command.math;

import java.util.List;
import java.util.Map;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.model.World;

public abstract class Function extends Math {

  public static int FUNCTION_PARAM_NUMBER = 1;
  public static int PARAM_INDEX = 0;

  protected double param;

  /***
   * Creates a Math Command that only takes one parameter
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   */
  public Function(List<Command> parameters)
      throws WrongParameterNumberException {

    super(parameters);
    checkForExactParameterLength(FUNCTION_PARAM_NUMBER);
  }

  /***
   * Makes param a private instance variable
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws WrongParameterTypeException if wrong parameter type passed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars)
      throws WrongParameterTypeException, WrongParameterNumberException {
    super.setUpExecution(world, userVars);
    param = getMathParam(PARAM_INDEX);
  }
}
