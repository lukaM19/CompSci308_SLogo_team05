package slogo.Command.Logic;

import java.util.List;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;
import slogo.model.World;

public class And extends TwoInputLogic{

  public And(World world, List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters);
  }

  @Override
  public Object execute() {
    return returnValues.get(param1 && param2);
  }
}
