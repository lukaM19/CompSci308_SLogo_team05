package slogo.command.actor.move.relative;

import java.util.List;
import slogo.command.actor.move.Move;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;

public abstract class ValueMove extends Move {
  public static final int RELATIVE_MOVE_PARAM_NUMBER = 1;
  public static final int RAW_VAL_INDEX = 0;

  private double rawValue;

  /***
   * Creates a new RelativeMove object that moves an actor based on its current location and orientation
   *
   * @param parameters - parameters for command
   */
  public ValueMove(List<Command> parameters) {
    super(parameters);
    setParamNumber(RELATIVE_MOVE_PARAM_NUMBER);
  }

  /***
   * Sets up raw value for relative movement
   *
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution() throws CommandException {
    super.setUpExecution();
    assignRawValue();
  }

  // assigns raw value based on scale
  private void assignRawValue() throws CommandException {
    rawValue = executeParameter(RAW_VAL_INDEX).returnVal();
    try {
      rawValue *= Double.parseDouble(getImpliedParameter(SCALE_KEY));
    } catch (NumberFormatException e) {
      throw new WrongImpliedParameterTypeException(getCommandName() + getImpliedParameter(SCALE_KEY));
    }
  }

  /***
   * @return raw value
   */
  protected double getRawValue() {
    return rawValue;
  }
}
