package slogo.command.actorcommand.Move;

import java.util.List;
import java.util.Map;
import slogo.command.util.Command;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.model.World;

public abstract class RelativeMove extends Move {
  public static final int RELATIVE_MOVE_PARAM_NUMBER = 1;
  public static final int RAW_VAL_INDEX = 0;

  protected double rawValue;
  protected AbsoluteMove absoluteMoveCommand;

  /***
   * Creates a new RelativeMove object that moves an actor based on its current location and orientation
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public RelativeMove(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {

    super(world, parameters, userVars);
    checkForExactParameterLength(RELATIVE_MOVE_PARAM_NUMBER);
    Command rawCommand = this.parameters.get(RAW_VAL_INDEX);

    try {
      rawValue = (Double) rawCommand.execute();
    } catch (Exception e) {
      throw new WrongParameterTypeException(getCommandName() + rawCommand);
    }
  }
}
