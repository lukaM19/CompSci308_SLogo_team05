package slogo.command.math.basicoperation;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.math.Operation;
import slogo.model.World;

public class Product extends Operation {

  /***
   * creates an Operation command that multiplies two values
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Product(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters, userVars);
  }

  /***
   * multiplies the two parameters
   *
   * @return product of parameters
   */
  @Override
  public Object execute() {
    return param1 * param2;
  }
}