package slogo.command.math.basicoperation;

import java.util.List;
import java.util.Optional;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.CommandResult;
import slogo.command.math.Operation;

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
  public Double run() {
    return param1 + param2;
  }
}
