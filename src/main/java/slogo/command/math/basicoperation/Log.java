package slogo.command.math.basicoperation;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.math.Function;
import slogo.model.World;

public class Log extends Function {

  /***
   * Creates a Function Command that logs a given parameter
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Log(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * logs the parameter
   *
   * @return log of the parameter
   */
  @Override
  public Object run() {
    return  Math.log(param);
  }
}
