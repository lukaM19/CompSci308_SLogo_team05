package slogo.command.logic.operator;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.logic.OneInputLogic;
import slogo.command.logic.TwoInputLogic;
import slogo.model.World;

public class Not extends OneInputLogic {

  /***
   * Creates a Logic Command that NOTs the input
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Not(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters, userVars);
  }

  /***
   * Performs NOT operation on the parameters
   *
   * @return corresponding double to true/false
   */
  @Override
  public Object execute() {
    return returnValues.get(!param);
  }
}
