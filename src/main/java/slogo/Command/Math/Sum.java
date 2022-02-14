package slogo.Command.Math;

import java.util.List;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;
import slogo.model.World;

public class Sum extends Operation{

  public Sum(World world, List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters);
  }

  @Override
  public Object execute() {
    return param1 + param2;
  }
}
