package slogo.command.actor.move.relative;

import java.util.ArrayList;
import java.util.List;

import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.command.value.GenericValue;
import slogo.parser.annotations.ImpliedArgument;
import slogo.parser.annotations.SlogoCommand;
import slogo.model.Actor;

@SlogoCommand(keywords = {"Left", "Right"}, arguments = 1)
@ImpliedArgument(keywords = {"Left", "Right"}, arg = "actorID", value = "0")
@ImpliedArgument(keywords = {"Left"}, arg = "scale", value = "-1")
@ImpliedArgument(keywords = {"Right"}, arg = "scale", value = "1")
public class ValueTurnRelative extends ValueMove {

  private List<ValueTurnAbsolute> turnCommands;

  /***
   * Creates a Command object that moves given a distance
   *
   * @param parameters - parameters for command
   */
  public ValueTurnRelative(List<Command> parameters) {
    super(parameters);
    turnCommands = new ArrayList<>();
  }

  /***
   * Sets angle difference and absolute angle
   *
   */
  @Override
  protected void calculateMovement(Actor actor) {
    double newAngle = actor.getHeading() + getRawValue();
    turnCommands.add(new ValueTurnAbsolute(List.of(new GenericValue(newAngle))));
    getImpliedParameters().put(SCALE_KEY, "1");
    turnCommands.get(turnCommands.size()-1).setImpliedParameters(getImpliedParameters());
  }

  /***
   * Changes the actor's heading
   *
   * @return angle changed
   */
  @Override
  public Double run() throws CommandException {
    for(Actor actor: getActors()) {
      calculateMovement(actor);
    }

    double lastTurn = DEFAULT_VALUE;
    for(Command turnCommand: turnCommands) {
      CommandResult result = turnCommand.execute(getWorld(), getUserVars());
      lastTurn = result.returnVal();
      mergeMoveInfos(result.moveInfos());
    }
    return lastTurn;
  }
}
