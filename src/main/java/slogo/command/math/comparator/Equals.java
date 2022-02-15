package slogo.command.math.comparator;

import static slogo.command.logic.Logic.returnValues;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.math.Operation;
import slogo.model.World;

public class Equals extends Operation {

  /***
   * Creates a Logic Command that takes two inputs and compares if the first equals the second
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Equals(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters, userVars);
  }

  /***
   * Determines if first param equals the second
   *
   * @return corresponding double to true/false
   */
  @Override
  public Object execute() {
    return returnValues.get(param1 == param2);
  }
}
