package slogo.Command;

import java.util.List;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.model.World;

public class GenericValue extends Command{
  public static final int GENERIC_VALUE_PARAM_NUMBER = 1;
  public static final int GENERIC_VALUE_INDEX = 0;

  private Object value;

  /***
   * Creates a Command object that just stores a value and returns it in execute
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   */
  public GenericValue(World world, List<Command> parameters) throws WrongParameterNumberException {
    super(world, parameters);
    checkForCorrectParameterLength(GENERIC_VALUE_PARAM_NUMBER);
    value = parameters.get(GENERIC_VALUE_INDEX).execute();
  }

  /***
   * Creates a command object with a given value
   *
   * @param value is the Command's value
   */
  public GenericValue(Object value) {
    super(null, null);
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
