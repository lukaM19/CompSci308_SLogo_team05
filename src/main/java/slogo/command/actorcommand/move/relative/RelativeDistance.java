package slogo.command.actorcommand.move.relative;

import java.util.List;
import java.util.Map;
import slogo.command.actorcommand.move.absolute.AbsoluteDistance;
import slogo.command.actorcommand.move.absolute.AbsoluteMove;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.CommandResult;
import slogo.command.value.GenericValue;
import slogo.model.World;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"Forward", "Backward"}, arguments = 1)
@ImpliedArgument(keywords = {"Forward", "Backward"}, arg = "actorID", value = "0")
@ImpliedArgument(keywords = {"Forward"}, arg = "scale", value = "1")
@ImpliedArgument(keywords = {"Backward"}, arg = "scale", value = "-1")
public class RelativeDistance extends RelativeMove{

  private AbsoluteMove absoluteDistanceCommand;
  private double newX;
  private double newY;
  private World world;
  private Map<String, Double> userVars;
  public static final double ANGLE_CONVERSION=Math.PI/ 180;

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
   */
  @Override
  protected void calculateMovement() {
    double angle = actor.getHeading();
    newX = rawValue*Math.sin(angle*ANGLE_CONVERSION);
    newY = rawValue*Math.cos(angle*ANGLE_CONVERSION);
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
