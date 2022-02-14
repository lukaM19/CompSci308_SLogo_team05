package slogo.Command;

import java.util.List;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.model.World;

public class GenericValue extends Command{
  public static final int GENERIC_VALUE_PARAM_NUMBER = 1;
  public static final int GENERIC_VALUE_INDEX = 0;

  private Object value;

  public GenericValue(World world, List<Command> parameters) throws WrongParameterNumberException {
    super(world, parameters);
    checkForCorrectParameterLength(GENERIC_VALUE_PARAM_NUMBER);
    value = parameters.get(GENERIC_VALUE_INDEX).execute();
  }

  public GenericValue(Object value) {
    super(null, null);
    this.value = value;
  }

  @Override
  public Object execute() {
    return value;
  }
}
