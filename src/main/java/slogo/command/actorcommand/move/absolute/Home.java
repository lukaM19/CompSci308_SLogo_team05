package slogo.command.actorcommand.move.absolute;

import static slogo.command.actorcommand.ActorCommand.ACTOR_ID_KEY;
import static slogo.command.general.Command.TEMP_FIX_KEY;

import java.util.List;
import java.util.Map;

import slogo.command.actorcommand.move.Move;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.value.GenericValue;
import slogo.model.Environment;
import slogo.model.World;
import slogo.parser.annotations.ImpliedArgument;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"Home"})
@ImpliedArgument(keywords = {"Home"}, arg = ACTOR_ID_KEY, value = "0")
@ImpliedArgument(keywords = {"SetPosition"}, arg = TEMP_FIX_KEY, value = "0")

public class Home extends Move {

  public static final GenericValue HOME_CORD = new GenericValue(0.0);

  private PointDistance moveCommand;

  /***
   * Creates command that returns the turtle home
   *
   * @param parameters - parameters for command
   */
  public Home(
      List<Command> parameters) {
    super(parameters);
  }


  /***
   * Sets up world and userVars
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Environment env) throws CommandException {
    this.world = world;
    this.environment = env;
    super.setUpExecution(world, env);
  }

  /***
   * Moves turtle home
   *
   * @return distance travelled
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected Double run() throws CommandException {
    calculateMovement();
    return executeInstanceCommand(moveCommand);
  }

  /***
   * Creates PointDistance command to origin
   */
  @Override
  protected void calculateMovement() {
    moveCommand = new PointDistance(List.of(HOME_CORD, HOME_CORD));
    moveCommand.setImpliedParameters(impliedParameters);
  }

}
