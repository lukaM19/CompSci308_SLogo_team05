package slogo.command.actorcommand.move.absolute;

import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import slogo.command.general.Command;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.model.World;

public class AbsoluteDistance extends AbsoluteMove{

  private double distance;

  /***
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public AbsoluteDistance(
      List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
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
  public Object run() {
    actor.setPosition(new Point2D(coords[X_INDEX], coords[Y_INDEX]));
    return distance;
  }
}
