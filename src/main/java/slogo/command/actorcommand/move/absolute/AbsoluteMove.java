package slogo.command.actorcommand.move.absolute;

import java.util.List;
import java.util.Map;
import slogo.command.actorcommand.move.Move;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
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
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public AbsoluteMove(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
    checkForExactParameterLength(ABSOLUTE_MOVE_PARAM_NUMBER);
  }

  @Override
  protected void setUpExecution(World world, Map<String, Object> userVars) throws CommandException {
    super.setUpExecution(world, userVars);

    coords = new double[ABSOLUTE_MOVE_PARAM_NUMBER];
    for (int i = 0; i < coords.length; i++) {
      Command currentCommand = this.parameters.get(i);
      try {
        coords[i] = (Double) currentCommand.execute(world, userVars);
      } catch (Exception e) {
        throw new WrongParameterTypeException(getCommandName() + currentCommand);
      }
    }
    calculateMovement();
  }
}
