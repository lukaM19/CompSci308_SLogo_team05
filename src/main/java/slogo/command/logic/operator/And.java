package slogo.command.logic.operator;

import java.util.List;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.logic.TwoInputLogic;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"And"}, arguments = 2)
public class And extends TwoInputLogic {

  /***
   * Creates a Logic Command that takes two inputs and ANDs them
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public And(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * Performs AND operation on the parameters
   *
   * @return corresponding double to true/false
   */
  @Override
  public Double run() {
    return RETURN_VALUES.get(getParam1() && getParam2());
  }
}
