package slogo.command.actor.move.relative;


import java.util.List;
import slogo.command.general.Command;
import slogo.model.Actor;
import slogo.model.MoveInfo;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"SetHeading"}, arguments = 1)
public class ValueTurnAbsolute extends ValueMove {

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
  protected void calculateMovement(Actor actor) {
    newAngle = getRawValue();
  }

  /***
   * Sets the actor's angle and then returns the degrees turned
   *
   * @return angle turned
   */
  @Override
  public Double run() {
    double prevHeading = DEFAULT_VALUE;
    for(Actor actor: getActors()) {
      calculateMovement(actor);
      prevHeading = actor.getHeading();
      actor.setHeading(newAngle);
      addMoveInfo(new MoveInfo(actor.getID(), actor.getPosition(), newAngle));
    }
    return Math.abs(newAngle - prevHeading);
  }
}
