package slogo.command.actorcommand.move.relative;

import java.util.List;
import java.util.Map;
import slogo.command.actorcommand.move.absolute.AbsoluteDistance;
import slogo.command.actorcommand.move.absolute.AbsoluteMove;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.value.GenericValue;
import slogo.model.World;

public class RelativeDistance extends RelativeMove{

  private AbsoluteMove absoluteMoveCommand;
  private double newX;
  private double newY;
  private World world;
  private Map<String, Object> userVars;

  /***
   * Creates a Command object that moves given a distance
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public RelativeDistance(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * Calculates new coordinates to move to given distance and current angle
   */
  @Override
  protected void calculateMovement() {
    double angle = actor.getHeading();
    newX = rawValue*Math.cos(angle);
    newY = rawValue*Math.sin(angle);
  }

  /***
   * Sets up absoluteMoveCommand and initializes other private instance variables
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Object> userVars) throws CommandException {
    super.setUpExecution(world, userVars);
    absoluteMoveCommand = new AbsoluteDistance(List.of(new GenericValue(newX), new GenericValue(newY)));
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
  public Object run() throws CommandException {
    return absoluteMoveCommand.execute(world, userVars);
  }
}
