package slogo.command.math.basicoperation;

import java.util.List;
import java.util.Map;
import slogo.command.general.Command;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.math.Operation;
import slogo.model.World;

public class Sum extends Operation {

  /***
   * Creates an Operation command that adds two values
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Sum(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * adds the two parameters
   *
   * @return sum of parameters
   */
  @Override
  public Object run() {
    return param1 + param2;
  }
}
