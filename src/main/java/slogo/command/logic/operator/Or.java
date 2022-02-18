package slogo.command.logic.operator;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.logic.TwoInputLogic;
import slogo.model.World;

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
  public Object run() {
    return returnValues.get(param1 || param2);
  }
}
