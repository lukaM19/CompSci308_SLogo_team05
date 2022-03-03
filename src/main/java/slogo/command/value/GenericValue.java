package slogo.command.value;

import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.ParameterNotFoundException;
import slogo.command.general.Command;

public class GenericValue extends Command {

  private Double value;

  /***
   * Creates a command object with a given value
   *
   * @param value is the Command's value
   */
  public GenericValue(Double value) {
    super(null);
    this.value = value;
  }

  /***
   * Defines value if it's null
   *
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution() throws CommandException {
    if(value == null) {
      throw new ParameterNotFoundException(getCommandName() + "null");
    }
  }

  /***
   * Returns value
   *
   * @return value passed in constructor
   */
  @Override
  protected Double run() {
    return value;
  }
}
