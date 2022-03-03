package slogo.command.logic;

import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.model.Environment;
import slogo.model.World;

public abstract class OneInputLogic extends Logic {

  public static final int OIL_PARAMETER_NUMBER = 1;
  public static final int PARAMETER_INDEX = 0;

  protected boolean param;

  /***
   * Creates a Logic Command that only takes one input
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public OneInputLogic(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * Makes param a private instance variable
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Environment env) throws CommandException {
    super.setUpExecution(world, env);
    checkForExactParameterLength(OIL_PARAMETER_NUMBER);
    param = evaluatedCommands.get(PARAMETER_INDEX);
  }
}
