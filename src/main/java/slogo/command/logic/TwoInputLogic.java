package slogo.command.logic;

import java.util.List;
import java.util.Map;
import slogo.command.general.Command;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.model.World;

public abstract class TwoInputLogic extends Logic{

  public static final int TIL_PARAMETER_NUMBER = 2;
  public static final int FIRST_PARAMETER_INDEX = 0;
  public static final int SECOND_PARAMETER_INDEX = 1;

  protected boolean param1;
  protected boolean param2;

  /***
   * Creates a Logic Command that only takes two inputs
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public TwoInputLogic(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters, userVars);
    checkForExactParameterLength(TIL_PARAMETER_NUMBER);
    param1 = evaluatedCommands.get(FIRST_PARAMETER_INDEX);
    param2 = evaluatedCommands.get(SECOND_PARAMETER_INDEX);
  }
}
