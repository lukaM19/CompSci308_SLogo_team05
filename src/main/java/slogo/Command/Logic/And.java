package slogo.Command.Logic;

import java.util.List;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;
import slogo.model.World;

public class And extends TwoInputLogic{

  /***
   * Creates a Logic Command that takes two inputs and evaluates like &&
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public And(World world, List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters);
  }

  /***
   * Performs && operation on the parameters
   *
   * @return corresponding double to true/false
   */
  @Override
  public Object execute() {
    return returnValues.get(param1 && param2);
  }
}
