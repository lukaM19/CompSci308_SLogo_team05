package slogo.Command;

import java.util.List;
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

  protected boolean correctParameterLength(int desiredSize) {
    return parameters.size() == desiredSize;
  }

  public abstract Object execute();
}
