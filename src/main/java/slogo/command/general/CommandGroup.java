package slogo.command.general;

import java.util.List;
import java.util.Map;
import slogo.model.World;

public class CommandGroup extends Command {

  public static final Object DEFAULT_RETURN = 0.0;

  /***
   * Creates a list of commands that are all executed at the same time. The last command's value
   * is returned
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   */
  public CommandGroup(World world, List<Command> parameters, Map<String, Object> userVars) {
    super(world, parameters, userVars);
  }

  /***
   * Executes all commands, returning the result of the last command executed
   * @return result of last command or default value if no commands
   */
  @Override
  public Object execute() {
    if(parameters.isEmpty()) return DEFAULT_RETURN;

    for(int i=0; i<getParametersSize() - 1; i++) {
      parameters.get(i).execute();
    }
    return parameters.get(getParametersSize() - 1).execute();
  }
}
