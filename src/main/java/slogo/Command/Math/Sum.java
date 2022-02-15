package slogo.Command.Math;

import java.util.List;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;
import slogo.model.World;

public class Sum extends Operation{

  /***
   * Creates an Operation command that adds two values
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Sum(World world, List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters);
  }

  /***
   * Adds the two parameters
   *
   * @return sum of parameters
   */
  @Override
  public Object execute() {
    return param1 + param2;
  }
}
