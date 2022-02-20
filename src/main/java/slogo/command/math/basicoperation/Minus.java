package slogo.command.math.basicoperation;

import java.util.List;
import java.util.Optional;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.command.math.Function;

public class Minus extends Function {

  public static final double NEGATIVE_SIGN = -1.0;

  /***
   * Creates a Function Command that negates a given parameter
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Minus(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * negates the parameter
   *
   * @return negative of the parameter
   */
  @Override
  public Double run() {
    return NEGATIVE_SIGN * param;
  }
}
