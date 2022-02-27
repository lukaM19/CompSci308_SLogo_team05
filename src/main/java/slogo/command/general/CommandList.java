package slogo.command.general;

import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.model.World;

public class CommandList extends Command {

  private World world;
  private Map<String, Double> userVars;

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
   *  @param world - the model to execute on
   * @param userVars - the map of user variables
   */
  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars) {
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
      executeParameter(i, world, userVars);
    }
    return executeParameter(getParametersSize() - 1, world, userVars).returnVal();
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
}
