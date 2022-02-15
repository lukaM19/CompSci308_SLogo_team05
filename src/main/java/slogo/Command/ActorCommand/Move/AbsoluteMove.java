package slogo.Command.ActorCommand.Move;

import java.util.List;
import slogo.Command.ActorCommand.Move.Move;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;
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
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public AbsoluteMove(World world, List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters);

    checkForCorrectParameterLength(ABSOLUTE_MOVE_PARAM_NUMBER);
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
