package slogo.Command.Math;

import java.util.List;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;

public class Sum extends Operation{

  public Sum(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  @Override
  public Object execute() {
    return param1 + param2;
  }
}
