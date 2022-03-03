package slogo.command.actorcommand.move.absolute;

import static slogo.command.actorcommand.ActorCommand.ACTOR_ID_KEY;
import static slogo.command.general.Command.TEMP_FIX_KEY;
import static slogo.command.logic.Logic.ACCEPTED_VALUES;
import static slogo.model.Turtle.PEN_KEY;

import java.util.List;

import javafx.geometry.Point2D;
import slogo.command.general.Command;
import slogo.model.MoveInfo;
import slogo.parser.annotations.ImpliedArgument;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"SetPosition"}, arguments = 2)
@ImpliedArgument(keywords = {"SetPosition"}, arg = ACTOR_ID_KEY, value = "0")
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