package slogo.command.actor.move.absolute;

import static slogo.command.general.Command.TEMP_FIX_KEY;

import java.util.List;
import slogo.command.actor.move.Move;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.value.GenericValue;
import slogo.model.Actor;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"Home"})
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
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution() throws CommandException {
    super.setUpExecution();
  }

  /***
   * Moves turtle home
   *
   * @return distance travelled
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected Double run() throws CommandException {
    calculateMovement(null);
    return executeInstanceCommand(moveCommand);
  }

  /***
   * Creates PointDistance command to origin
   */
  @Override
  protected void calculateMovement(Actor actor) {
    moveCommand = new PointDistance(List.of(HOME_CORD, HOME_CORD));
    moveCommand.setImpliedParameters(getImpliedParameters());
  }

}
