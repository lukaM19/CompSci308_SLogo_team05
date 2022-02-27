package slogo.command.actorcommand.move.absolute;

import static slogo.command.actorcommand.ActorCommand.ACTOR_ID_KEY;

import java.util.List;
import java.util.Map;
import slogo.command.actorcommand.move.relative.ValueTurnAbsolute;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.command.value.GenericValue;
import slogo.model.MoveInfo;
import slogo.model.World;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"SetTowards"}, arguments = 1)
@ImpliedArgument(keywords = {"SetTowards"}, arg = ACTOR_ID_KEY, value = "0")
public class PointTurn extends PointMove {

  public static final double HALF_ROTATION = Math.PI;
  public static final double ZERO = 0.0;

  private ValueTurnAbsolute turnCommand;

  /***
   * Creates an AbsoluteMove Command that turns the actor towards a given point
   *
   * @param parameters - parameters for command
   */
  public PointTurn(
      List<Command> parameters) {
    super(parameters);
  }

  /***
   * Calculates angle given a point using arctan and angle adjustments
   */
  @Override
  protected void calculateMovement() {
    double yDiff = coords[Y_INDEX] - actor.getPosition().getY();
    double xDiff = coords[X_INDEX] - actor.getPosition().getX();
    double newAngle;

    if(xDiff == ZERO) {
      newAngle = actor.getHeading();
    }
    else {
      newAngle = Math.atan(yDiff / xDiff) + (xDiff < ZERO ? HALF_ROTATION : ZERO);
    }

    turnCommand = new ValueTurnAbsolute(List.of(new GenericValue(newAngle)));
    turnCommand.setImpliedParameters(impliedParameters);
  }

  /***
   * Sets the actor's angle and then returns the degrees turned
   *
   * @return angle turned
   */
  @Override
  public Double run() throws CommandException {
    return executeInstanceCommand(turnCommand);
  }
}