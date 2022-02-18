package slogo.command.general;

import java.util.List;
import java.util.Map;
import slogo.command.exception.ActorNotFoundException;
import slogo.command.exception.CommandException;
import slogo.model.World;

public class CommandGroup extends Command {

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
<<<<<<< HEAD:src/main/java/slogo/command/general/CommandList.java
  @Override
  protected void setUpExecution(World world, Map<String, Object> userVars) {
    this.world = world;
    this.userVars = userVars;
=======
  public CommandGroup(World world, List<Command> parameters, Map<String, Object> userVars) {
    super(world, parameters, userVars);
>>>>>>> parent of f6de553 (interpreter tweaks):src/main/java/slogo/command/general/CommandGroup.java
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
