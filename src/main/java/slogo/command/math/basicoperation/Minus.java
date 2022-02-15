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
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Minus(World world, List<Command> parameters,
      Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters, userVars);
  }

  /***
   * negates the parameter
   *
   * @return negative of the parameter
   */
  @Override
  public Object execute() {
    return  NEGATIVE_SIGN * param;
  }
}
