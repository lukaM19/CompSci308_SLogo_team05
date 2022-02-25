package slogo.command.actorcommand.move.relative;

import static slogo.command.actorcommand.ActorCommand.ACTOR_ID_KEY;

import java.util.List;
import slogo.command.general.Command;
import slogo.model.MoveInfo;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"SetHeading"}, arguments = 1)
@ImpliedArgument(keywords = {"SetHeading"}, arg = ACTOR_ID_KEY, value = "0")
public class ValueTurnAbsolute extends ValueDistance {

  private double newAngle;

  /***
   * Creates an AbsoluteMove Command that turns the actor towards a given point
   *
   * @param parameters - parameters for command
   */
  public ValueTurnAbsolute(
      List<Command> parameters) {
    super(parameters);
  }

  /***
   * Calculates angle given a point using arctan and angle adjustments
   */
  @Override
  protected void calculateMovement() {
    newAngle = rawValue;
  }

  /***
   * Sets the actor's angle and then returns the degrees turned
   *
   * @return angle turned
   */
  @Override
  public Double run() {
    double prevHeading = actor.getHeading();
    actor.setHeading(newAngle);
    addMoveInfo(new MoveInfo(actor.getID(), actor.getPosition(), newAngle));
    return Math.abs(newAngle - prevHeading);
  }
}
