package slogo.command.math.comparator;

import static slogo.command.logic.Logic.returnValues;

import java.util.List;
import java.util.Optional;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.command.math.Operation;

public class NotEquals extends Operation {

  /***
   * Creates a Logic Command that takes two inputs and compares if the first does not equal the second
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public NotEquals(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * Determines if first param does not equal the second
   *
   * @return corresponding double to true/false
   */
  public Double run() {
    return returnValues.get(param1 != param2);
  }
}
