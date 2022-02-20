package slogo.command.math.basicoperation;

import java.util.List;
import java.util.Optional;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.command.math.Operation;

public class Quotient extends Operation {

  /***
   * creates an Operation command that divides two values
   *
   * @param parameters - parameters for command
   */
  public Quotient(List<Command> parameters) {
    super(parameters);
  }

  /***
   * divides the two parameters
   *
   * @return quotient of parameters
   */
  @Override
  public Double run() {
    return param1 / param2;
  }
}
