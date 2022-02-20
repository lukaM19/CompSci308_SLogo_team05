package slogo.command.control;

import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.model.World;

public class ForLoop extends Control {

  public static final int FOR_LOOP_PARAMETER_NUMBER = 4;
  public static final int FOR_LOOP_COUNTER_INDEX = 0;
  public static final int FOR_LOOP_LIMIT_INDEX = 1;
  public static final int FOR_LOOP_INCREMENT_INDEX = 2;
  public static final int FOR_LOOP_BODY_INDEX = 3;

  protected String counterKey;
  protected long counter;
  protected long increment;
  protected long limit;
  protected Command body;

  private World world;
  private Map<String, Double> userVars;

  /***
   * Creates a Control Command that represents a for loop a given amount of times
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public ForLoop(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
    checkForExactParameterLength(FOR_LOOP_PARAMETER_NUMBER);
  }

  /***
   * Assigns loop variables
   *
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  private void assignLoopVariables(World world, Map<String, Double> userVars)
      throws CommandException {
    counterKey = getImpliedParameter(VAR_NAME_KEY);
    counter = Math.round(parameters.get(FOR_LOOP_COUNTER_INDEX).execute(world, userVars).returnVal());
    limit = Math.round(parameters.get(FOR_LOOP_LIMIT_INDEX).execute(world, userVars).returnVal());
    increment = Math.round(parameters.get(FOR_LOOP_INCREMENT_INDEX).execute(world, userVars).returnVal());
    body = parameters.get(FOR_LOOP_BODY_INDEX);
  }

  /***
   * Sets up loop variables and loop expression
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
    assignLoopVariables(world, userVars);
    this.world = world;
    this.userVars = userVars;
  }

  /***
   * Runs a for loop
   *
   * @return result of last command executed in for loop
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected CommandResult run() throws CommandException {
    CommandResult returnVal = DEFAULT_VALUE;
    for(long i = counter; i < limit; i += increment) {
      returnVal = body.execute(world, userVars);
      userVars.put(counterKey, (double) i);
    }
    return returnVal;
  }
}
