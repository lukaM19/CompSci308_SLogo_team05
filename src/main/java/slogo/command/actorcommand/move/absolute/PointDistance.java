package slogo.command.actorcommand.move.absolute;

import static slogo.command.actorcommand.ActorCommand.ACTOR_ID_KEY;
import static slogo.command.logic.Logic.ACCEPTED_VALUES;
import static slogo.model.Turtle.PEN_KEY;

import java.util.List;

import javafx.geometry.Point2D;
import slogo.command.general.Command;
import slogo.model.MoveInfo;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"SetPosition"}, arguments = 2)
@ImpliedArgument(keywords = {"SetPosition"}, arg = ACTOR_ID_KEY, value = "0")

public class PointDistance extends PointMove {

  private double distance;

  /***
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
  protected void calculateMovement() {
    distance = Math.sqrt(square(coords[X_INDEX]) + square(coords[Y_INDEX]));
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
    Point2D moveTo = new Point2D(coords[X_INDEX], coords[Y_INDEX]);
    MoveInfo moveInfo = new MoveInfo(actor.getID(), actor.getPosition(), moveTo, actor.getHeading(),
        ACCEPTED_VALUES.getOrDefault(actor.getVal(PEN_KEY), false));
    actor.setPosition(moveTo);
    addMoveInfo(moveInfo);
    return distance;
  }
}
