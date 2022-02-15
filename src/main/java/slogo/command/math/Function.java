package slogo.command.math;

import java.util.List;
import java.util.Map;
import slogo.command.util.Command;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.model.World;

public abstract class Function extends Math {

  public static int FUNCTION_PARAM_NUMBER = 1;
  public static int PARAM_INDEX = 0;

  protected double param;

  /***
   * Creates a Math Command that only takes one parameter
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Function(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {

    super(world, parameters, userVars);
    checkForExactParameterLength(FUNCTION_PARAM_NUMBER);
    param = getMathParam(PARAM_INDEX);
  }
}
