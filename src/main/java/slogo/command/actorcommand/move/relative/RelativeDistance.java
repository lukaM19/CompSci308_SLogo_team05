package slogo.command.actorcommand.move.relative;

import static slogo.command.actorcommand.ActorCommand.ACTOR_ID_KEY;
import static slogo.command.actorcommand.ActorCommand.SCALE_KEY;

import java.util.List;
import java.util.Map;
import slogo.command.actorcommand.move.absolute.AbsoluteDistance;
import slogo.command.actorcommand.move.absolute.AbsoluteMove;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.CommandResult;
import slogo.command.value.GenericValue;
import slogo.model.World;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"Forward", "Backward"}, arguments = 1)
@ImpliedArgument(keywords = {"Forward", "Backward"}, arg = ACTOR_ID_KEY, value = "0")
@ImpliedArgument(keywords = {"Forward"}, arg = SCALE_KEY, value = "1")
@ImpliedArgument(keywords = {"Backward"}, arg = SCALE_KEY, value = "-1")
public class RelativeDistance extends RelativeMove{

  private AbsoluteMove absoluteDistanceCommand;
  private double newX;
  private double newY;
  private World world;
  private Map<String, Double> userVars;

  /***
   * Creates a Command object that moves given a distance
   *
   * @param parameters - parameters for command
   */
  public RelativeDistance(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Calculates new coordinates to move to given distance and current angle
   *
   * @throws WrongImpliedParameterTypeException if scale is not a double
   */
  @Override
  protected void calculateMovement() throws WrongImpliedParameterTypeException {
    double angle = actor.getHeading();
    try {
      rawValue *= Double.parseDouble(impliedParameters.get(SCALE_KEY));
    } catch (NumberFormatException e) {
      throw new WrongImpliedParameterTypeException(getCommandName() + impliedParameters.get(SCALE_KEY));
    }
    newX = rawValue*Math.sin(angle);
    newY = rawValue*Math.cos(angle);
  }

  /***
   * Sets up absoluteMoveCommand and initializes other private instance variables
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
    super.setUpExecution(world, userVars);
    absoluteDistanceCommand = new AbsoluteDistance(List.of(new GenericValue(actor.getPosition().getX() + newX), new GenericValue(actor.getPosition().getY() +newY)));
    absoluteDistanceCommand.setImpliedParameters(impliedParameters);
    this.world = world;
    this.userVars = userVars;
  }

  /***
   * Executes the absoluteMoveCommand to appropriate point
   *
   * @return distance moved
   * @throws CommandException if command cannot be executed
   */
  @Override
  public Double run() throws CommandException {
    CommandResult res = absoluteDistanceCommand.execute(world, userVars);
    mergeMoveInfos(res.moveInfos());
    return res.returnVal();
  }
}
