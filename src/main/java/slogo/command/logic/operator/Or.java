package slogo.command.logic.operator;

import java.util.List;
import java.util.Optional;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.command.logic.TwoInputLogic;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"Or"}, arguments = 2)
public class Or extends TwoInputLogic {

  /***
   * Creates a Logic Command that takes two inputs and ORs them
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Or(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * Performs OR operation on the parameters
   *
   * @return corresponding double to true/false
   */
  @Override
  public Double run() {
    return returnValues.get(param1 || param2);
  }
}
