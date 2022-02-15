package slogo.command.util;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.model.World;

public abstract class Command {
  protected List<Command> parameters;
  private final String commandName;
  protected World world;
  protected Map<String, Object> userVars;

  /***
   * Command object used by interpreter to execute various actions
   *  @param world - the model to execute on
   * @param parameters - the parameters for command
   * @param userVars - the map of user variables
   */
  public Command(World world, List<Command> parameters, Map<String, Object> userVars) {
    this.commandName = this.getClass().getSimpleName() + ": ";
    this.parameters = parameters;
    this.world = world;
    this.userVars = userVars;
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
   * Executes a given command
   *
   * @return result of execution
   */
  public abstract Object execute();
}
