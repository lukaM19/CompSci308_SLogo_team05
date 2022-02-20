package slogo.command.value;

import java.util.Map;
import java.util.Optional;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.ParameterNotFoundException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.model.World;

public class GenericValue extends Command {
  public static final int GENERIC_VALUE_INDEX = 0;

  protected Double value;

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
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
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
