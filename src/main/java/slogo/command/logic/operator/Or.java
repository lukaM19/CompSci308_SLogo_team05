package slogo.command.logic.operator;

import java.util.List;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.logic.TwoInputLogic;
import slogo.parser.annotations.SlogoCommand;

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
    return RETURN_VALUES.get(getParam1() || getParam2());
  }
}
