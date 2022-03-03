package slogo.command.control;

import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.UserVarMapNotFoundException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.command.value.UserValue;
import slogo.model.Environment;
import slogo.model.World;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"For"}, arguments = 2)
public class ForLoop extends Control {

  public static final int FOR_LOOP_PARAMETER_NUMBER = 2;

  public static final int FOR_LOOP_LIST_INDEX = 0;
  public static final int FOR_LOOP_LIST_LEN = 4;
  public static final int FOR_LOOP_VAR_INDEX = 0;
  public static final int FOR_LOOP_START_INDEX = 1;
  public static final int FOR_LOOP_END_INDEX = 2;
  public static final int FOR_LOOP_INCREMENT_INDEX = 3;

  public static final int FOR_LOOP_BODY_INDEX = 1;

  protected String counterKey;
  protected double counter;
  protected double increment;
  protected double limit;

  private World world;

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
  private void assignLoopVariables(CommandList loopVars, World world, Environment env)
      throws CommandException {
    Command loopVarCmd = loopVars.getParameterCommand(FOR_LOOP_VAR_INDEX);
    if(!(loopVarCmd instanceof UserValue)) {
      throw new WrongParameterTypeException("First value in a for loop's list must be a variable name");
    }
    counterKey = ((UserValue)loopVarCmd).getVarName();
    counter = executeCommand(loopVars.getParameterCommand(FOR_LOOP_START_INDEX), world, env).returnVal();
    limit = executeCommand(loopVars.getParameterCommand(FOR_LOOP_END_INDEX), world, env).returnVal();
    increment = executeCommand(loopVars.getParameterCommand(FOR_LOOP_INCREMENT_INDEX), world, env).returnVal();
  }

  /***
   * Sets up loop variables and loop expression
   *
   * @param world - the model to execute on
   * @param env - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Environment env) throws CommandException {
    checkForExactParameterLength(FOR_LOOP_PARAMETER_NUMBER);
    if(!(getParameterCommand(FOR_LOOP_LIST_INDEX) instanceof CommandList loopVars)
            || loopVars.getParametersSize() != FOR_LOOP_LIST_LEN) {
      throw new WrongParameterTypeException("First parameter to a for loop must be a list with 4 elements"); // FIXME localization
    }
    assignLoopVariables(loopVars, world, env);
    this.world = world;
    this.environment = env;
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
        environment.setVariable(counterKey, i);
      } catch(NullPointerException e) {
        throw new UserVarMapNotFoundException(getCommandName());
      }
      returnVal = executeParameter(FOR_LOOP_BODY_INDEX, world, environment).returnVal();
    }
    return returnVal;
  }
}
