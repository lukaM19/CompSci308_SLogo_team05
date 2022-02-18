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
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public RelativeTurn(List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters, userVars);
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
   * @param world
   */
  @Override
  public Object execute(World world) {
    actor.setHeading(actor.getHeading() + angleDifference);
    return angleDifference;
  }
}
