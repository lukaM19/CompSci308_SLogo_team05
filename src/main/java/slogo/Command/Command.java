package slogo.Command;

import java.util.List;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.model.World;

public abstract class Command {
  protected List<Command> parameters;
  private final String commandName;
  protected World world;

  public Command(World world, List<Command> parameters) {
    this.parameters = parameters;
    this.commandName = this.getClass().getSimpleName() + ": ";
    this.world = world;
  }

  protected String getCommandName() {
    return commandName;
  }

  protected int getParametersSize() {
    return parameters.size();
  }

  protected void checkForCorrectParameterLength(int desiredSize)
      throws WrongParameterNumberException {
    if(getParametersSize() != desiredSize) {
      throw new WrongParameterNumberException(commandName + getParametersSize());
    }
  }

  public abstract Object execute();
}
