package slogo.command.actor.move.absolute;

import static slogo.command.general.Command.TEMP_FIX_KEY;
import static slogo.model.Turtle.PEN_KEY;
import static slogo.model.Turtle.PEN_UP;

import java.util.List;
import slogo.command.actor.move.Move;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.model.Actor;
import slogo.model.MoveInfo;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"ClearScreen"})
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
  }

  /***
   * Clears screen and moves turtle home
   *
   * @return distance travelled
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected Double run() throws CommandException {
    calculateMovement(null);
    MoveInfo clearScreen = null;
    for(Actor actor: actors) {
      if (actor.hasVal(PEN_KEY)) {
        actor.putVal(PEN_KEY, PEN_UP);
      }
      if(clearScreen == null) {
        clearScreen = new MoveInfo(actor.getID(), actor.getPosition(), actor.getPosition(),
            actor.getHeading(), false, true);
      }
    }
    addMoveInfo(clearScreen);
    return executeCommand(moveCommand).returnVal();
  }

  /***
   * Creates new home command
   */
  @Override
  protected void calculateMovement(Actor actor) {
    moveCommand = new Home(List.of());
    moveCommand.setImpliedParameters(getImpliedParameters());
  }

}
