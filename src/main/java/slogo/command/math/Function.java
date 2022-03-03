package slogo.command.math;

import java.util.List;
import java.util.Map;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterException;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.model.Environment;
import slogo.model.World;

public abstract class Function extends Math {

  public static final int FUNCTION_PARAM_NUMBER = 1;
  public static final int PARAM_INDEX = 0;

  protected double param;

  /***
   * Creates a Math Command that only takes one parameter
   *
   * @param parameters - parameters for command
   */
  public Function(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Makes param a private instance variable
   *
   * @param world - the model to execute on
   * @param env - the map of user variables
   * @throws WrongParameterTypeException if wrong parameter type passed
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws ImpliedParameterException if issues with implied parameters
   */
  @Override
  protected void setUpExecution(World world, Environment env)
      throws WrongParameterTypeException, WrongParameterNumberException, ImpliedParameterException {
    checkForExactParameterLength(FUNCTION_PARAM_NUMBER);
    super.setUpExecution(world, env);
    param = getMathParam(PARAM_INDEX);
  }
}
