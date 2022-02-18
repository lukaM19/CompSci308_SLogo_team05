package slogo.command.general;

import java.util.List;
import java.util.Map;
import slogo.command.exception.ActorNotFoundException;
import slogo.command.exception.CommandException;
import slogo.model.World;

public class CommandList extends Command {

  public static final Object DEFAULT_RETURN = 0.0;

  private World world;
  private Map<String, Object> userVars;

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
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   */
  @Override
  protected void setUpExecution(World world, Map<String, Object> userVars) {
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
  protected Object run() throws CommandException {
    if(parameters.isEmpty()) return DEFAULT_RETURN;

    for(int i=0; i<getParametersSize() - 1; i++) {
      parameters.get(i).execute(world, userVars);
    }
    return parameters.get(getParametersSize() - 1).execute(world, userVars);
  }
}
