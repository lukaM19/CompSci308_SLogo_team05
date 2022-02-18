package slogo.command.math.trig;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.math.Function;
import slogo.model.World;

public class Sine extends Function {

  /***
   * Creates a Function Command that returns sin(param)
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Sine(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * does sin(param)
   *
   * @return sin(param)
   */
  @Override
  public Object run() {
    return java.lang.Math.sin(param);
  }
}
