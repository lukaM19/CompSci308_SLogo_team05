package slogo.command.value;

import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.general.Command;
import slogo.model.World;

public class GenericValue extends Command {
  public static final int GENERIC_VALUE_PARAM_NUMBER = 1;
  public static final int GENERIC_VALUE_INDEX = 0;

  protected Object value;

  /***
   * Creates a Command object that just stores a value and returns it in execute
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   */
  public GenericValue(List<Command> parameters, Map<String, Object> userVars) throws WrongParameterNumberException {
    super(parameters);
    checkForExactParameterLength(GENERIC_VALUE_PARAM_NUMBER);
  }

  /***
   * Creates a command object with a given value
   *
   * @param value is the Command's value
   */
  public GenericValue(Object value) {
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
  protected void setUpExecution(World world, Map<String, Object> userVars) throws CommandException {
    if(value == null) {
      value = parameters.get(GENERIC_VALUE_INDEX).execute(world, userVars);
    }
  }

  /***
   * Returns value
   *
   * @return value passed in constructor
   */
  @Override
  protected Object run() {
    return value;
  }
}
