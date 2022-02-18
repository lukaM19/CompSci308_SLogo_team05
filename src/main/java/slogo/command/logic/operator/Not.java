package slogo.command.logic.operator;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.logic.OneInputLogic;
import slogo.model.World;

public class Not extends OneInputLogic {

  /***
   * Creates a Logic Command that NOTs the input
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Not(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * Performs NOT operation on the parameters
   *
   * @return corresponding double to true/false
   */
  @Override
  public Object run() {
    return returnValues.get(!param);
  }
}
