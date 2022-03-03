package slogo.command.general;

import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.exception.CommandNotRunException;
import slogo.model.World;

public class CommandList extends Command {

  private World world;
  private Map<String, Double> userVars;
  private List<CommandResult> results;

  /***
   * Creates a list of commands that are all executed at the same time. The last command's value
   * is returned
   *
   * @param parameters - parameters for command
   */
  public CommandList(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Sets up variables needed for run()
   */
  @Override
  protected void setUpExecution() {
    this.world = world;
    this.userVars = userVars;
  }

  /***
   * Runs all the commands in the list
   *
   * @return result of last command or default value if no commands
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected Double run() throws CommandException {
    if(getParametersSize() == 0) {
      return DEFAULT_VALUE;
    }

    for(int i = 0; i < getParametersSize() - 1; i++) {
      results.add(executeParameter(i, world, userVars));
    }
    results.add(executeParameter(getParametersSize() - 1, world, userVars));
    return results.get(results.size()-1).returnVal();
  }

  /**
   * Returns the parameter of this list with the specified index
   * Public in CommandList to allow other commands to do something more than execute the list
   * @param index the index of the command to return
   * @return the command at that index of the list
   */
  @Override
  public Command getParameterCommand(int index) {
    return super.getParameterCommand(index);
  }

  public List<CommandResult> getAllResults() throws CommandNotRunException {
    if(results != null)
      return results;
    throw new CommandNotRunException(getCommandName());
  }
}
