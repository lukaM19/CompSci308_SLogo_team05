package slogo.command.actor.move.absolute;

import static slogo.command.actor.ActorCommand.ACTOR_ID_KEY;
import static slogo.command.general.Command.TEMP_FIX_KEY;
import static slogo.model.Turtle.PEN_KEY;
import static slogo.model.Turtle.PEN_UP;

import java.util.List;
import slogo.command.actor.move.Move;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.model.MoveInfo;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

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
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution() throws CommandException {
    super.setUpExecution();
    this.world = world;
    this.userVars = userVars;
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