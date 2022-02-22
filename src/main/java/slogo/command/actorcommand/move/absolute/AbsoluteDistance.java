package slogo.command.actorcommand.move.absolute;

import java.util.List;

import javafx.geometry.Point2D;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.model.MoveInfo;
import slogo.model.Turtle;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"SetPosition"}, arguments = 2)
@ImpliedArgument(keywords = {"SetPosition"}, arg = "actorID", value = "0")
public class AbsoluteDistance extends AbsoluteMove{

  private double distance;

  /***
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public AbsoluteDistance(
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
    // FIXME Possible violation of things we're supposed to do
    boolean penDown = actor instanceof Turtle && ((Turtle) actor).isPenDown();
    MoveInfo moveInfo = new MoveInfo(actor.getID(), actor.getPosition(), moveTo, actor.getHeading(), penDown);
    actor.setPosition(moveTo);
    addMoveInfo(moveInfo);
    return distance;
  }
}
