package slogo.command.actorcommand.move.relative;

import java.util.List;
import java.util.Map;
import slogo.command.actorcommand.move.absolute.AbsoluteDistance;
import slogo.command.actorcommand.move.absolute.AbsoluteMove;
import slogo.command.general.Command;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.value.GenericValue;
import slogo.model.World;

public class RelativeDistance extends RelativeMove{

  private AbsoluteMove absoluteMoveCommand;
  private double newX;
  private double newY;

  /***
   * Creates a Command object that moves given a distance
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public RelativeDistance(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters, userVars);
    absoluteMoveCommand = new AbsoluteDistance(world, List.of(new GenericValue(newX), new GenericValue(newY)), userVars);
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
   * Executes the absoluteMoveCommand to appropriate point
   *
   * @return distance moved
   */
  @Override
  public Object execute() {
    return absoluteMoveCommand.execute();
  }
}
