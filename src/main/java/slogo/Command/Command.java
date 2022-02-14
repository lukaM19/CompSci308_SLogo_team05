package slogo.Command;

import java.util.List;

public abstract class Command {
  protected List<Command> parameters;
  private final String commandName;

  public Command(List<Command> parameters) {
    this.parameters = parameters;
    this.commandName = this.getClass().getSimpleName() + ": ";
  }

  protected String getCommandName() {
    return commandName;
  }

  public abstract Object execute();
}
