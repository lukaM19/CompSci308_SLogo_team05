package slogo.command.logic;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.model.World;

public abstract class OneInputLogic extends Logic{

  public static final int OIL_PARAMETER_NUMBER = 1;
  public static final int PARAMETER_INDEX = 0;

  protected boolean param;

  /***
   * Creates a Logic Command that only takes one input
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public OneInputLogic(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters, userVars);
    checkForExactParameterLength(OIL_PARAMETER_NUMBER);
    param = evaluatedCommands.get(PARAMETER_INDEX);
  }
}
