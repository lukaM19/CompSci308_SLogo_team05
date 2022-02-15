package slogo.command.value;

import java.util.List;
import java.util.Map;
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
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   */
  public GenericValue(World world, List<Command> parameters, Map<String, Object> userVars) throws WrongParameterNumberException {
    super(world, parameters, userVars);
    checkForExactParameterLength(GENERIC_VALUE_PARAM_NUMBER);
    value = parameters.get(GENERIC_VALUE_INDEX).execute();
  }

  /***
   * Creates a command object with a given value
   *
   * @param value is the Command's value
   */
  public GenericValue(Object value) {
    super(null, null, null);
    this.value = value;
  }

  /***
   * Returns the value of this Command
   *
   * @return value
   */
  @Override
  public Object execute() {
    return value;
  }
}
