package slogo.command.actorcommand.move.relative;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.model.MoveInfoTest;
import slogo.model.World;

public class RelativeTurn extends RelativeMove{

  private double angleDifference;
  private double absoluteAngle;

  /***
   * Creates a Command object that moves given a distance
   *
   * @param parameters - parameters for command
   */
  public RelativeTurn(List<Command> parameters) {
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
    addMoveInfo(new MoveInfoTest(absoluteAngle));
    return angleDifference;
  }
}
