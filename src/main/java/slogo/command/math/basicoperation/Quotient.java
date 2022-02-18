package slogo.command.math.basicoperation;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.math.Operation;
import slogo.model.World;

public class Quotient extends Operation {

  /***
   * creates an Operation command that divides two values
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Quotient(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * divides the two parameters
   *
   * @return quotient of parameters
   */
  @Override
  public Object run() {
    return param1 / param2;
  }
}
