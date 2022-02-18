package slogo.command.math.comparator;

import static slogo.command.logic.Logic.returnValues;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.math.Operation;
import slogo.model.World;

public class Less extends Operation {

  /***
   * Creates a Logic Command that takes two inputs and compares if the first is less than the second
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Less(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * Determines if first param is less than the second
   *
   * @return corresponding double to true/false
   */
  @Override
  public Object run() {
    return returnValues.get(param1 < param2);
  }
}
