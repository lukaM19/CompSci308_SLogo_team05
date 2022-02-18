package slogo.command.general;

import java.util.List;
import java.util.Map;
import slogo.command.exception.ActorNotFoundException;
import slogo.command.exception.CommandException;
import slogo.command.exception.WrongParameterNumberException;
import slogo.model.World;

public abstract class Command {
  public Double DEFAULT_VALUE = 0.0;

  protected List<Command> parameters;
  private final String commandName;

  /***
   * Command object used by interpreter to execute various actions
   *
   * @param parameters - the parameters for command
   */
  public Command(List<Command> parameters) {
    this.commandName = this.getClass().getSimpleName() + ": ";
    this.parameters = parameters;
  }

  /***
   * Gets the name of the current command object
   *
   * @return command class name
   */
  protected String getCommandName() {
    return commandName;
  }

  /***
   * Gets size of the parameters list
   *
   * @return size of parameters list
   */
  protected int getParametersSize() {
    return parameters.size();
  }

  /***
   * Checks if the parameter size is the same as expected
   *
   * @param desiredSize is the correct size for the parameter list
   * @throws WrongParameterNumberException if the sizes are mismatched
   */
  protected void checkForExactParameterLength(int desiredSize)
      throws WrongParameterNumberException {
    if(getParametersSize() != desiredSize) {
      throw new WrongParameterNumberException(commandName + getParametersSize());
    }
  }

  /***
   * Checks if the parameter size is at least as expected
   *
   * @param minSize is the minimum size for the parameter list
   * @throws WrongParameterNumberException if minSize is larger than the length of the parameter list
   */
  protected void checkForMinParameterLength(int minSize) throws WrongParameterNumberException {
    if(getParametersSize() < minSize) {
      throw new WrongParameterNumberException(commandName + getParametersSize());
    }
  }

  /***
   * Sets up parameters prior to execution
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  protected abstract void setUpExecution(World world, Map<String, Object> userVars) throws CommandException;

  /***
   * Runs command after setup
   *
   * @return return value of command
   * @throws CommandException if command cannot be executed
   */
  protected abstract Object run() throws CommandException;

  /***
   * Executes a given command
   *
   * @return result of execution
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  public final Object execute(World world, Map<String, Object> userVars) throws CommandException {
    setUpExecution(world, userVars);
    return run();
  }
}
