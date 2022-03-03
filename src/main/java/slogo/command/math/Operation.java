package slogo.command.math;

import java.util.List;
import java.util.Map;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterException;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.model.Environment;
import slogo.model.World;

public abstract class Operation extends Math {
  public static final int OPERATION_PARAM_NUMBER = 2;
  public static final int FIRST_PARAM_INDEX = 0;
  public static final int SECOND_PARAM_INDEX = 1;

  protected double param1;
  protected double param2;

  /***
   * Creates a Math Command that takes two parameters
   *
   * @param parameters - parameters for command
   */
  public Operation(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Makes params private instance variables
   *
   * @param world - the model to execute on
   * @param env - the map of user variables
   * @throws WrongParameterTypeException if wrong parameter type passed
   * @throws WrongParameterNumberException if wrong number of parameters passed
   */
  @Override
  protected void setUpExecution(World world, Environment env)
      throws WrongParameterTypeException, WrongParameterNumberException, ImpliedParameterException {
    super.setUpExecution(world, env);
    checkForExactParameterLength(OPERATION_PARAM_NUMBER);
    param1 = getMathParam(FIRST_PARAM_INDEX);
    param2 = getMathParam(SECOND_PARAM_INDEX);
  }
}
