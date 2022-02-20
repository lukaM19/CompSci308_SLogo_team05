package slogo.command.control;

import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.CommandResult;
import slogo.command.logic.Logic;
import slogo.command.general.Command;
import slogo.model.World;

public class Conditional extends Control {

  public static final int IF_PARAMETER_COUNT = 2;
  public static final int IF_ELSE_PARAMETER_COUNT = 3;
  public static final int IF_BLOCK_INDEX = 1;
  public static final int ELSE_BLOCK_INDEX = 2;

  private World world;
  private Map<String, Double> userVars;

  /***
   * Creates a Control Command that evaluates commands based on if the given expr is true or false
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Conditional(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * Sets up if statement header evaluation and checks for body size
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
    if(getParametersSize() != IF_PARAMETER_COUNT && getParametersSize() != IF_ELSE_PARAMETER_COUNT) {
      throw new WrongParameterNumberException(getCommandName() + getParametersSize());
    }
    this.world = world;
    this.userVars = userVars;
  }

  /***
   * Runs the corresponding Command based on what expression evaluated to
   *
   * @return result of executing the correct Command
   * @throws CommandException if command cannot be executed
   */
  @Override
  public Double run() throws CommandException {
    if(evaluateExpression(world, userVars)) {
      return executeParameter(IF_BLOCK_INDEX, world, userVars).returnVal();
    } else {
      return executeParameter(ELSE_BLOCK_INDEX, world, userVars).returnVal();
    }
  }
}
