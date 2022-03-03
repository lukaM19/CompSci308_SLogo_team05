package slogo.command.math;

import java.util.List;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterException;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;

public abstract class Function extends Math {

  public static int FUNCTION_PARAM_NUMBER = 1;
  public static int PARAM_INDEX = 0;

  private double param;

  /***
   * Creates a Math Command that only takes one parameter
   *
   * @param parameters - parameters for command
   */
  public Function(List<Command> parameters) {
    super(parameters);
    setParamNumber(FUNCTION_PARAM_NUMBER);
  }

  /***
   * Makes param a private instance variable
   *
   * @throws WrongParameterTypeException if wrong parameter type passed
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws ImpliedParameterException if issues with implied parameters
   */
  @Override
  protected void setUpExecution()
      throws WrongParameterTypeException, WrongParameterNumberException, ImpliedParameterException {
    super.setUpExecution();
    param = getMathParam(PARAM_INDEX);
  }

  /***
   * @return param
   */
  protected double getParam() {
    return param;
  }
}
