package slogo.Command.Math;

import java.util.List;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;
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
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Function(World world, List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {

    super(world, parameters);
    checkForCorrectParameterLength(FUNCTION_PARAM_NUMBER);
    param = getMathParam(PARAM_INDEX);
  }
}
