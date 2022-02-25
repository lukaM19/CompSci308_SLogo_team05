package slogo.command.actorcommand.move.relative;

import static slogo.command.actorcommand.ActorCommand.ACTOR_ID_KEY;
import static slogo.command.actorcommand.ActorCommand.SCALE_KEY;

import java.util.List;

import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.model.MoveInfo;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"Left", "Right"}, arguments = 1)
@ImpliedArgument(keywords = {"Left", "Right"}, arg = "actorID", value = "0")
@ImpliedArgument(keywords = {"Left"}, arg = "scale", value = "-1")
@ImpliedArgument(keywords = {"Right"}, arg = "scale", value = "1")
public class RelativeTurn extends RelativeMove {

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
   *
   * @throws WrongImpliedParameterTypeException if scale is not a double
   */
  @Override
  protected void calculateMovement() throws WrongImpliedParameterTypeException {
    try {
      rawValue *= Double.parseDouble(impliedParameters.get(SCALE_KEY));
    } catch (NumberFormatException e) {
      throw new WrongImpliedParameterTypeException(getCommandName() + impliedParameters.get(SCALE_KEY));
    }
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
