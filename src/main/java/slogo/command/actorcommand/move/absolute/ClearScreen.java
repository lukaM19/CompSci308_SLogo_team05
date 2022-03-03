package slogo.command.actorcommand.move.absolute;

import static slogo.command.actorcommand.ActorCommand.ACTOR_ID_KEY;
import static slogo.command.general.Command.TEMP_FIX_KEY;
import static slogo.model.Turtle.PEN_KEY;
import static slogo.model.Turtle.PEN_UP;

import java.util.List;
import java.util.Map;
import slogo.command.actorcommand.move.Move;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.model.Environment;
import slogo.model.MoveInfo;
import slogo.model.World;
import slogo.parser.annotations.ImpliedArgument;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"ClearScreen"})
@ImpliedArgument(keywords = {"ClearScreen"}, arg = ACTOR_ID_KEY, value = "0")
@ImpliedArgument(keywords = {"SetPosition"}, arg = TEMP_FIX_KEY, value = "0")

public class ClearScreen extends Move {
  
  private Home moveCommand;

  /***
   * Creates command that returns the turtle home
   *
   * @param parameters - parameters for command
   */
  public ClearScreen(
      List<Command> parameters) {
    super(parameters);
  }


  /***
   * Sets up world and user vars
   *
   * @param world - the model to execute on
   * @param env - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Environment env) throws CommandException {
    super.setUpExecution(world, env);
    this.world = world;
    this.environment = env;
  }

  /***
   * Clears screen and moves turtle home
   *
   * @return distance travelled
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected Double run() throws CommandException {
    calculateMovement();
    if(actor.hasVal(PEN_KEY)) actor.putVal(PEN_KEY, PEN_UP);
    MoveInfo clearScreen = new MoveInfo(actor.getID(), actor.getPosition(), actor.getPosition(), actor.getHeading(), false, true);
    addMoveInfo(clearScreen);
    return executeInstanceCommand(moveCommand);
  }

  /***
   * Creates new home command
   */
  @Override
  protected void calculateMovement() {
    moveCommand = new Home(List.of());
    moveCommand.setImpliedParameters(impliedParameters);
  }

}
