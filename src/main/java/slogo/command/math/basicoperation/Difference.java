package slogo.command.math.basicoperation;

import java.util.List;
import java.util.Optional;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.command.math.Operation;
import slogo.parser.SlogoCommand;

@Deprecated
public class Difference extends Operation {
  /***
   * Creates an Operation command that subtracts two values
   *
   * @param parameters - parameters for command
   */
  public Difference(List<Command> parameters) {
    super(parameters);
  }

  /***
   * subtracts the two parameters
   *
   * @return difference of parameters
   */
  @Override
  public Double run() {
    return param1 - param2;
  }
}
