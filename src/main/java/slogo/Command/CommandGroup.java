package slogo.Command;

import java.util.List;
import slogo.model.World;

public class CommandGroup extends Command {

  /***
   * Creates a list of commands that are all executed at the same time. The last command's value
   * is returned
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   */
  public CommandGroup(World world, List<Command> parameters) {
    super(world, parameters);
  }

  /***
   * Executes all commands, returning the result of the last command executed
   * @return result of last command
   */
  @Override
  public Object execute() {
    for(int i=0; i<getParametersSize() - 1; i++) {
      parameters.get(i).execute();
    }
    return parameters.get(getParametersSize() - 1).execute();
  }
}
