package slogo.command.math.basicoperation;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.math.Function;
import slogo.model.World;

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
  public Object run() {
    return  NEGATIVE_SIGN * param;
  }
}
