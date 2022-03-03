package slogo.command.actorcommand.move.relative;

import java.util.List;

import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.command.value.GenericValue;
import slogo.parser.annotations.ImpliedArgument;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"Left", "Right"}, arguments = 1)
@ImpliedArgument(keywords = {"Left", "Right"}, arg = "actorID", value = "0")
@ImpliedArgument(keywords = {"Left"}, arg = "scale", value = "-1")
@ImpliedArgument(keywords = {"Right"}, arg = "scale", value = "1")
public class ValueTurnRelative extends ValueMove {

  private ValueTurnAbsolute turnCommand;

  /***
   * Creates a Command object that moves given a distance
   *
   * @param parameters - parameters for command
   */
  public ValueTurnRelative(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Sets angle difference and absolute angle
   *
   * @throws WrongImpliedParameterTypeException if scale is not a double
   */
  @Override
  protected void calculateMovement() throws WrongImpliedParameterTypeException {
    double newAngle = actor.getHeading() + rawValue;
    turnCommand = new ValueTurnAbsolute(List.of(new GenericValue(newAngle)));
    impliedParameters.put(SCALE_KEY, "1");
    turnCommand.setImpliedParameters(impliedParameters);
  }

  /***
   * Changes the actor's heading
   *
   * @return angle changed
   */
  @Override
  public Double run() throws CommandException {
    return executeInstanceCommand(turnCommand);
  }
}
