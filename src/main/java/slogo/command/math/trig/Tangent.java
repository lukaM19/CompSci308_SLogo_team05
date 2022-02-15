package slogo.command.math.trig;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.math.Function;
import slogo.model.World;

public class Tangent extends Function {

  /***
   * Creates a Function Command that returns tan(param)
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Tangent(World world, List<Command> parameters,
      Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters, userVars);
  }

  /***
   * does tan(param)
   *
   * @return tan(param)
   */
  @Override
  public Object execute() {
    return java.lang.Math.tan(param);
  }
}
