package slogo.command.math.random;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.math.Operation;
import slogo.model.World;

public class RandomRange extends Operation {

  /***
   * Creates an Operation command that returns a random value in the given range
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public RandomRange(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters, userVars);
    makeRangePositive();
  }

  /***
   * Makes param2 > param1 for proper execution
   */
  private void makeRangePositive() {
    if(param1 > param2) {
      double temp = param1;
      param1 = param2;
      param2 = temp;
    }
  }

  /***
   * gets a random value from the range
   *
   * @return random value
   */
  @Override
  public Object execute() {
    return java.lang.Math.random() * (param2 - param1) + param1;
  }
}
