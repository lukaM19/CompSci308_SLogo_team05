package slogo.command.control;

import java.util.List;
import java.util.Map;
import slogo.command.exception.ActorNotFoundException;
import slogo.command.exception.CommandException;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.logic.Logic;
import slogo.command.general.Command;
import slogo.model.World;

public class Conditional extends Control {

  public static final int CONDITIONAL_BODY_NUMBER = 2;
  public static final int CONDITIONAL_BODY_ONE_INDEX = 0;
  public static final int CONDITIONAL_BODY_TWO_INDEX = 1;

  private World world;
  private Map<String, Object> userVars;

  /***
   * Creates a Control Command that evaluates commands based on if the given expr is true or false
   *
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Conditional(List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters, userVars);
  }

  /***
   * Evaluates if the expression Command is true or false
   *
   * @return true if expression is true, false otherwise
   * @throws WrongParameterTypeException if expression.execute() cannot be converted to a boolean
   */
  private boolean evaluateExpression(World world, Map<String, Object> userVars)
      throws CommandException {
    Object result = expression.execute(world, userVars);
    if(Logic.acceptedValues.containsKey(result)) {
      return Logic.acceptedValues.get(result);
    }
    throw new WrongParameterTypeException(getCommandName() + result);
  }

  /***
   * Sets up if statement header evaluation and checks for body size
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Object> userVars) throws CommandException {
    super.setUpExecution(world, userVars);
    if(bodyCommands.size() != CONDITIONAL_BODY_NUMBER) {
      throw new WrongParameterNumberException(getCommandName() + bodyCommands.size());
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
  public Object run() throws CommandException {
    if(evaluateExpression(world, userVars)) {
      return bodyCommands.get(CONDITIONAL_BODY_ONE_INDEX).execute(world, userVars);
    }
    else {
      return bodyCommands.get(CONDITIONAL_BODY_TWO_INDEX).execute(world, userVars);
    }
  }
}
