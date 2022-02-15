package slogo.Command.ActorCommand.Move;

import java.util.List;
import javafx.geometry.Point2D;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;
import slogo.model.World;

public class AbsoluteDistanceMove extends AbsoluteMove{

  private double distance;

  /***
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public AbsoluteDistanceMove(World world,
      List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters);

  }

  /***
   * Calculates movement given a point using Pythagorean theorem
   */
  @Override
  protected void calculateMovement() {
    distance = Math.sqrt(Math.pow(coords[X_INDEX], 2) + Math.pow(coords[Y_INDEX], 2));
  }

  /***
   * Sets the actor's position and then returns the distance travelled
   * @return distance travelled
   */
  @Override
  public Object execute() {
    actor.setPosition(new Point2D(coords[X_INDEX], coords[Y_INDEX]));
    return distance;
  }
}
