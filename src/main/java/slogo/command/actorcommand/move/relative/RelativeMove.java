package slogo.command.actorcommand.move.relative;

import java.util.List;
import java.util.Map;
import slogo.command.actorcommand.move.Move;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.model.World;

public abstract class RelativeMove extends Move {
  public static final int RELATIVE_MOVE_PARAM_NUMBER = 1;
  public static final int RAW_VAL_INDEX = 0;

  protected double rawValue;

  /***
   * Creates a new RelativeMove object that moves an actor based on its current location and orientation
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public RelativeMove(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * Sets up raw value for relative movement
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
    super.setUpExecution(world, userVars);
    checkForExactParameterLength(RELATIVE_MOVE_PARAM_NUMBER);
    rawValue = executeParameter(RAW_VAL_INDEX, world, userVars).returnVal();
    calculateMovement();
  }
}
