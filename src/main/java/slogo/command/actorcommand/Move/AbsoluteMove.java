package slogo.command.actorcommand.Move;

import java.util.List;
import java.util.Map;
import slogo.command.util.Command;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.model.World;

public abstract class AbsoluteMove extends Move {
  public static final int ABSOLUTE_MOVE_PARAM_NUMBER = 2;
  public static final int X_INDEX = 0;
  public static final int Y_INDEX = 1;

  protected double[] coords;

  /***
   * Creates a Command Object that acts on an actor given coordinates
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public AbsoluteMove(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters, userVars);

    checkForExactParameterLength(ABSOLUTE_MOVE_PARAM_NUMBER);
    coords = new double[ABSOLUTE_MOVE_PARAM_NUMBER];

    for (int i = 0; i < coords.length; i++) {
      Command currentCommand = this.parameters.get(i);
      try {
        coords[i] = (Double) currentCommand.execute();
      } catch (Exception e) {
        throw new WrongParameterTypeException(getCommandName() + currentCommand);
      }
    }
  }
}
