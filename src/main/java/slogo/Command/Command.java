package slogo.Command;

import java.util.List;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.model.World;

public abstract class Command {
  protected List<Command> parameters;
  private final String commandName;
  protected World world;

  /***
   * Command object used by interpreter to execute various actions
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   */
  public Command(World world, List<Command> parameters) {
    this.parameters = parameters;
    this.commandName = this.getClass().getSimpleName() + ": ";
    this.world = world;
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
  protected void checkForCorrectParameterLength(int desiredSize)
      throws WrongParameterNumberException {
    if(getParametersSize() != desiredSize) {
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
