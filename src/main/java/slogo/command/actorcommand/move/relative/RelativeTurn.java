package slogo.command.actorcommand.move.relative;

import java.util.List;

import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.model.MoveInfo;

public class RelativeTurn extends RelativeMove{

  private double angleDifference;
  private double absoluteAngle;

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
   * Sets angle difference and absolute angle
   */
  @Override
  protected void calculateMovement() {
    angleDifference = rawValue;
    absoluteAngle = actor.getHeading() + angleDifference;
  }

  /***
   * Changes the actor's heading
   *
   * @return angle changed
   */
  @Override
  public Double run() {
    actor.setHeading(actor.getHeading() + angleDifference);
    addMoveInfo(new MoveInfo(actor.getID(), actor.getPosition(), actor.getHeading()));
    return angleDifference;
  }
}
