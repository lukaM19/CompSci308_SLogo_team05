package slogo.command.actorcommand.move.relative;

import java.util.List;
import java.util.Map;
import slogo.command.actorcommand.move.Move;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.model.Environment;
import slogo.model.World;

public abstract class ValueMove extends Move {
  public static final int RELATIVE_MOVE_PARAM_NUMBER = 1;
  public static final int RAW_VAL_INDEX = 0;

  protected double rawValue;

  /***
   * Creates a new RelativeMove object that moves an actor based on its current location and orientation
   *
   * @param parameters - parameters for command
   */
  public ValueMove(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Sets up raw value for relative movement
   *
   * @param world - the model to execute on
   * @param env - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Environment env) throws CommandException {
    super.setUpExecution(world, env);
    checkForExactParameterLength(RELATIVE_MOVE_PARAM_NUMBER);
    assignRawValue();
    this.world = world;
    this.environment = env;
    calculateMovement();
  }

  private void assignRawValue() throws CommandException {
    rawValue = executeParameter(RAW_VAL_INDEX, world, environment).returnVal();
    try {
      rawValue *= Double.parseDouble(impliedParameters.get(SCALE_KEY));
    } catch (NumberFormatException e) {
      throw new WrongImpliedParameterTypeException(getCommandName() + impliedParameters.get(SCALE_KEY));
    }
  }
}
