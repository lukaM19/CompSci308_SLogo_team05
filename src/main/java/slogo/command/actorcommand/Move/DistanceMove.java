package slogo.command.actorcommand.Move;

import java.util.List;
import java.util.Map;
import slogo.command.util.Command;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.util.GenericValue;
import slogo.model.World;

public class DistanceMove extends RelativeMove{

  /***
   * Creates a Command object that moves given a distance
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public DistanceMove(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters, userVars);
  }

  /***
   * Calculates new coordinates to move to given distance and current angle
   *
   * @throws WrongParameterTypeException if too many/few parameters
   * @throws WrongParameterNumberException if parameters have incorrect type
   */
  @Override
  protected void calculateMovement()
      throws WrongParameterTypeException, WrongParameterNumberException {
    double angle = actor.getHeading();
    double newX = rawValue*Math.cos(angle);
    double newY = rawValue*Math.sin(angle);
    absoluteMoveCommand = new AbsoluteDistanceMove(world, List.of(new GenericValue(newX), new GenericValue(newY)), userVars);
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
