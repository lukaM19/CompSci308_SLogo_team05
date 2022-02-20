package slogo.command.math.basicoperation;

import java.util.List;
import java.util.Optional;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.command.math.Operation;

public class Power extends Operation {

  /***
   * creates an Operation command that exponentiates two values
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Power(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * exponentiates the two parameters
   *
   * @return exponent of parameters
   */
  @Override
  public Double run() {
    return Math.pow(param1, param2);
  }
}
