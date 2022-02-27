package slogo.command.actorcommand.move.absolute;

import static slogo.command.actorcommand.ActorCommand.ACTOR_ID_KEY;
import static slogo.command.logic.Logic.ACCEPTED_VALUES;
import static slogo.model.Turtle.PEN_KEY;

import java.util.List;
import javafx.geometry.Point2D;
import slogo.command.actorcommand.move.Move;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.command.value.GenericValue;
import slogo.model.MoveInfo;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"Home"})
@ImpliedArgument(keywords = {"Home"}, arg = ACTOR_ID_KEY, value = "0")

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
   * Moves turtle home
   *
   * @return distance travelled
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected Double run() throws CommandException {
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
