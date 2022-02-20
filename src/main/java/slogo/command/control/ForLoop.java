package slogo.command.control;

import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.UserVarMapNotFoundException;
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
  protected double counter;
  protected double increment;
  protected double limit;

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
  }

  /***
   * Assigns loop variables
   *
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  private void assignLoopVariables(World world, Map<String, Double> userVars)
      throws CommandException {
    counterKey = getImpliedParameter(VAR_NAME_KEY);
    counter = executeParameter(FOR_LOOP_COUNTER_INDEX, world, userVars).returnVal();
    limit = executeParameter(FOR_LOOP_LIMIT_INDEX, world, userVars).returnVal();
    increment = executeParameter(FOR_LOOP_INCREMENT_INDEX, world, userVars).returnVal();
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
    checkForExactParameterLength(FOR_LOOP_PARAMETER_NUMBER);
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
  protected Double run() throws CommandException {
    Double returnVal = DEFAULT_VALUE;
    for(double i = counter; i < limit; i += increment) {
      try {
        userVars.put(counterKey, i);
      } catch(NullPointerException e) {
        throw new UserVarMapNotFoundException(getCommandName());
      }
      returnVal = executeParameter(FOR_LOOP_BODY_INDEX, world, userVars).returnVal();
    }
    return returnVal;
  }
}
