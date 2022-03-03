package slogo.command.actor.move.relative;

import static slogo.command.actor.ActorCommand.SCALE_KEY;

import java.util.ArrayList;
import java.util.List;
import slogo.command.actor.move.absolute.PointDistance;
import slogo.command.actor.move.absolute.PointMove;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.command.value.GenericValue;
import slogo.model.Actor;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"Forward", "Backward"}, arguments = 1)
@ImpliedArgument(keywords = {"Forward"}, arg = SCALE_KEY, value = "1")
@ImpliedArgument(keywords = {"Backward"}, arg = SCALE_KEY, value = "-1")
public class ValueDistance extends ValueMove {

  public static final double DEG_TO_RAD = Math.PI/180.0;

  private List<PointMove> absoluteDistanceCommands;
  private double newX;
  private double newY;

  /***
   * Creates a Command object that moves given a distance
   *
   * @param parameters - parameters for command
   */
  public ValueDistance(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Calculates new coordinates to move to given distance and current angle
   *
   * @throws WrongImpliedParameterTypeException if scale is not a double
   */
  @Override
  protected void calculateMovement(Actor actor) throws WrongImpliedParameterTypeException {
    double angle = actor.getHeading()*DEG_TO_RAD;
    newX = getRawValue()*Math.sin(angle);
    newY = getRawValue()*Math.cos(angle);
  }

  /***
   * Sets up absoluteMoveCommand and initializes other private instance variables
   *
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution() throws CommandException {
    super.setUpExecution();
    absoluteDistanceCommands = new ArrayList<>();
    for(Actor actor: getActors()) {
      absoluteDistanceCommands.add(new PointDistance(
          List.of(new GenericValue(actor.getPosition().getX() + newX),
              new GenericValue(actor.getPosition().getY() + newY))));
      absoluteDistanceCommands.get(absoluteDistanceCommands.size() - 1)
          .setImpliedParameters(getImpliedParameters());
    }
  }

  /***
   * Executes the absoluteMoveCommand to appropriate point
   *
   * @return distance moved
   * @throws CommandException if command cannot be executed
   */
  @Override
  public Double run() throws CommandException {
    double lastMove = DEFAULT_VALUE;
    for(Command absoluteDistanceCommand: absoluteDistanceCommands) {
      lastMove = absoluteDistanceCommand.execute(getWorld(), getUserVars()).returnVal();
    }
    return lastMove;
  }
}
