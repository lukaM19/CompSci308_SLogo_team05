package slogo.command.actor.move.absolute;

import static slogo.command.general.Command.TEMP_FIX_KEY;
import static slogo.command.logic.Logic.ACCEPTED_VALUES;
import static slogo.model.Turtle.PEN_STATE_KEY;

import java.util.List;

import javafx.geometry.Point2D;
import slogo.command.general.Command;
import slogo.model.Actor;
import slogo.model.MoveInfo;
import slogo.parser.annotations.ImpliedArgument;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"SetPosition"}, arguments = 2)
//TODO: fix this: currently needed because parser reads in array of implied arguments, so need 2
@ImpliedArgument(keywords = {"SetPosition"}, arg = TEMP_FIX_KEY, value = "0")
public class PointDistance extends PointMove {

  private double distance;

  /***
   * Creates command that goes to a designated point
   *
   * @param parameters - parameters for command
   */
  public PointDistance(
      List<Command> parameters) {
    super(parameters);

  }

  /***
   * Calculates movement given a point using Pythagorean theorem
   */
  @Override
  protected void calculateMovement(Actor actor) {
    distance = Math.sqrt(square(getCoords()[X_INDEX] - actor.getPosition().getX()) + square(getCoords()[Y_INDEX] - actor.getPosition().getY()));
  }

  /***
   * Squares a given value
   * @param val to square
   * @return val squared
   */
  private double square(double val) {
    return Math.pow(val, 2);
  }

  /***
   * Sets the actor's position and then returns the distance travelled
   * @return distance travelled
   */
  @Override
  public Double run() {
    Point2D moveTo = new Point2D(getCoords()[X_INDEX], getCoords()[Y_INDEX]);
    for(Actor actor: getActors()) {
      MoveInfo moveInfo = new MoveInfo(actor.getID(), actor.getPosition(), moveTo,
          actor.getHeading(),
          ACCEPTED_VALUES.getOrDefault(actor.getVal(PEN_STATE_KEY), false));
      actor.setPosition(moveTo);
      addMoveInfo(moveInfo);
      calculateMovement(actor);
    }
    return distance;
  }
}
