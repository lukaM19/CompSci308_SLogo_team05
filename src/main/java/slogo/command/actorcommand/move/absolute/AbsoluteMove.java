package slogo.command.actorcommand.move.absolute;

import java.util.List;
import java.util.Map;
import slogo.command.actorcommand.move.Move;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.CommandResult;
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
   */
  public AbsoluteMove(List<Command> parameters) {
    super(parameters);
  }

  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
    super.setUpExecution(world, userVars);
    checkForExactParameterLength(ABSOLUTE_MOVE_PARAM_NUMBER);
    coords = new double[ABSOLUTE_MOVE_PARAM_NUMBER];
    for (int i = 0; i < coords.length; i++) {
      coords[i] = executeParameter(i, world, userVars).returnVal();
    }
    calculateMovement();
  }
}
