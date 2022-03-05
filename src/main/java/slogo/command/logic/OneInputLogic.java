package slogo.command.logic;

import java.util.List;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;

public abstract class OneInputLogic extends Logic {

  public static final int OIL_PARAMETER_NUMBER = 1;
  public static final int PARAMETER_INDEX = 0;

  private boolean param;

  /***
   * Creates a Logic Command that only takes one input
   *
   * @param parameters - parameters for command
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public OneInputLogic(List<Command> parameters) throws WrongParameterTypeException {
    super(parameters);
    setParamNumber(OIL_PARAMETER_NUMBER);
  }

  /***
   * Makes param a private instance variable
   *
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution() throws CommandException {
    super.setUpExecution();
    param = getEvaluatedCommands().get(PARAMETER_INDEX);
  }

  /***
   * @return param
   */
  protected boolean getParam() {
    return param;
  }
}
