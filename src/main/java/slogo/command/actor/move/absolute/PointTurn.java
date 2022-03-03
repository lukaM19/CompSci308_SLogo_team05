package slogo.command.actor.move.absolute;

import static slogo.command.general.Command.TEMP_FIX_KEY;

import java.util.ArrayList;
import java.util.List;
import slogo.command.actor.move.relative.ValueTurnAbsolute;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.value.GenericValue;
import slogo.model.Actor;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"SetTowards"}, arguments = 1)
@ImpliedArgument(keywords = {"SetPosition"}, arg = TEMP_FIX_KEY, value = "0")
public class PointTurn extends PointMove {

  public static final double HALF_ROTATION = Math.PI;
  public static final double ZERO = 0.0;

  private List<ValueTurnAbsolute> turnCommands;

  /***
   * Creates an AbsoluteMove Command that turns the actor towards a given point
   *
   * @param parameters - parameters for command
   */
  public PointTurn(
      List<Command> parameters) {
    super(parameters);
    turnCommands = new ArrayList<>();
  }

  /***
   * Calculates angle given a point using arctan and angle adjustments
   */
  @Override
  protected void calculateMovement(Actor actor) {
    double yDiff = getCoords()[Y_INDEX] - actor.getPosition().getY();
    double xDiff = getCoords()[X_INDEX] - actor.getPosition().getX();
    double newAngle;

    if(xDiff == ZERO) {
      newAngle = actor.getHeading();
    }
    else {
      newAngle = Math.toDegrees(Math.atan(yDiff / xDiff) + (xDiff < ZERO ? HALF_ROTATION : ZERO));
    }

    turnCommands.add(new ValueTurnAbsolute(List.of(new GenericValue(newAngle))));
    turnCommands.get(turnCommands.size() - 1).setImpliedParameters(getImpliedParameters());
  }

  /***
   * Sets the actor's angle and then returns the degrees turned
   *
   * @return angle turned
   */
  @Override
  public Double run() throws CommandException {
    for(Actor actor: getActors()) {
      calculateMovement(actor);
    }

    double lastTurn = DEFAULT_VALUE;
    for(Command turnCommand: turnCommands) {
      lastTurn = turnCommand.execute(getWorld(), getUserVars()).returnVal();
    }
    return lastTurn;
  }
}
