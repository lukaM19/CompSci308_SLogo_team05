package slogo.Command.Math;

import java.util.List;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;
import slogo.model.World;

public abstract class Operation extends Math{
  public static int OPERATION_PARAM_NUMBER = 2;
  public static int FIRST_PARAM_INDEX = 0;
  public static int SECOND_PARAM_INDEX = 0;

  protected double param1;
  protected double param2;

  public Operation(World world, List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {

    super(world, parameters);
    checkForCorrectParameterLength(OPERATION_PARAM_NUMBER);
    param1 = getParam(FIRST_PARAM_INDEX);
    param2 = getParam(SECOND_PARAM_INDEX);
  }

  private boolean correctParameterNumber() {
    return parameters.size() == OPERATION_PARAM_NUMBER;
  }
}