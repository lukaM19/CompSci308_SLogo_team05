package slogo.command.actorcommand.move.relative;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.model.World;

public class RelativeTurn extends RelativeMove{

  private double angleDifference;
  /***
   * Creates a Command object that moves given a distance
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public RelativeTurn(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * Sets angle difference
   */
  @Override
  protected void calculateMovement() {
    angleDifference = rawValue;
  }

  /***
   * Changes the actor's heading
   *
   * @return angle changed
   */
  @Override
  public Object run() {
    actor.setHeading(actor.getHeading() + angleDifference);
    return angleDifference;
  }
}
