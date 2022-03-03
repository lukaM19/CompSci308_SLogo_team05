package slogo.command.world;

import static slogo.command.general.Command.VAR_NAME_KEY;
import static slogo.model.World.ACTIVE_TURTLE_KEY;
import static slogo.model.World.TURTLE_NUM_KEY;

import java.util.List;
import slogo.command.exception.CommandException;
import slogo.command.exception.worldexception.UnknownWorldValueException;
import slogo.command.general.Command;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"ID", "Turtles"})
@ImpliedArgument(keywords = {"ID"}, arg = VAR_NAME_KEY, value = ACTIVE_TURTLE_KEY)
@ImpliedArgument(keywords = {"Turtles"}, arg = VAR_NAME_KEY, value = TURTLE_NUM_KEY)

public class WorldQuery extends WorldCommand {

  protected String queryVar;

  /***
   * Creates a Command that gets a given attribute from the world
   *
   * @param parameters - parameters for command
   */
  public WorldQuery(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Sets up the variable to get
   *
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution() throws CommandException {
    queryVar = getImpliedParameter(VAR_NAME_KEY);
    if(!world.hasKey(queryVar)) {
      throw new UnknownWorldValueException(getCommandName() + queryVar);
    }
  }

  /***
   * Returns the desired world parameter, .get() will always be successful because it was checked
   * previously.
   *
   * @return the desired actor parameter
   */
  @Override
  public Double run() {
    return world.getVal(queryVar);
  }
}
