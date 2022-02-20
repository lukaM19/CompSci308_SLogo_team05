package slogo.command.math.random;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.command.math.Operation;

public class RandomRange extends Operation {

  /***
   * Creates an Operation command that returns a random value in the given range
   *
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public RandomRange(List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
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
  public Double run() {
    makeRangePositive();
    return Math.random() * (param2 - param1) + param1;
  }
}
