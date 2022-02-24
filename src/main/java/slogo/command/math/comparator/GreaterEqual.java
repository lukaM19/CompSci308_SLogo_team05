package slogo.command.math.comparator;

import static slogo.command.logic.Logic.returnValues;

import java.util.List;
import java.util.Optional;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.command.math.Operation;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"GreaterEqual"}, arguments = 2)
public class GreaterEqual extends Operation {

  /***
   * Creates a Logic Command that takes two inputs and compares if the first is greater than or equal to the second
   *
   * @param parameters - parameters for command
   */
  public GreaterEqual(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Determines if first param is greater than or equal to the second
   *
   * @return corresponding double to true/false
   */
  @Override
  public Double run() {
    return returnValues.get(param1 >= param2);
  }
}
