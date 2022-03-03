package slogo.command.actorcommand.move.relative;

import static slogo.command.actorcommand.ActorCommand.ACTOR_ID_KEY;
import static slogo.command.actorcommand.ActorCommand.SCALE_KEY;

import java.util.List;
import java.util.Map;
import slogo.command.actorcommand.move.absolute.PointDistance;
import slogo.command.actorcommand.move.absolute.PointMove;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.command.value.GenericValue;
import slogo.model.Environment;
import slogo.model.World;
import slogo.parser.annotations.ImpliedArgument;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"Forward", "Backward"}, arguments = 1)
@ImpliedArgument(keywords = {"Forward", "Backward"}, arg = ACTOR_ID_KEY, value = "0")
@ImpliedArgument(keywords = {"Forward"}, arg = SCALE_KEY, value = "1")
@ImpliedArgument(keywords = {"Backward"}, arg = SCALE_KEY, value = "-1")
public class ValueDistance extends ValueMove {

  public static final double DEG_TO_RAD = Math.PI/180.0;

  private PointMove absoluteDistanceCommand;
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
  protected void calculateMovement() throws WrongImpliedParameterTypeException {
    double angle = actor.getHeading()*DEG_TO_RAD;
    newX = rawValue*Math.sin(angle);
    newY = rawValue*Math.cos(angle);
  }

  /***
   * Sets up absoluteMoveCommand and initializes other private instance variables
   *
   * @param world - the model to execute on
   * @param env - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Environment env) throws CommandException {
    super.setUpExecution(world, env);
    absoluteDistanceCommand = new PointDistance(List.of(new GenericValue(actor.getPosition().getX() + newX), new GenericValue(actor.getPosition().getY() +newY)));
    absoluteDistanceCommand.setImpliedParameters(impliedParameters);
  }

  /***
   * Executes the absoluteMoveCommand to appropriate point
   *
   * @return distance moved
   * @throws CommandException if command cannot be executed
   */
  @Override
  public Double run() throws CommandException {
    return executeInstanceCommand(absoluteDistanceCommand);
  }
}
