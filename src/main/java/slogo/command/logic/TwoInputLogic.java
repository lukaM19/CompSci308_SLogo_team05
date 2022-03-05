package slogo.command.logic;

import java.util.List;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;

public abstract class TwoInputLogic extends Logic{

  public static final int TIL_PARAMETER_NUMBER = 2;
  public static final int FIRST_PARAMETER_INDEX = 0;
  public static final int SECOND_PARAMETER_INDEX = 1;

  private boolean param1;
  private boolean param2;

  /***
   * Creates a Logic Command that only takes two inputs
   *
   * @param parameters - parameters for command
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public TwoInputLogic(List<Command> parameters)
      throws WrongParameterTypeException {
    super(parameters);
    setParamNumber(TIL_PARAMETER_NUMBER);
  }

  /***
   * Makes params private instance variables
   *
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution() throws CommandException {
    super.setUpExecution();
    param1 = getEvaluatedCommands().get(FIRST_PARAMETER_INDEX);
    param2 = getEvaluatedCommands().get(SECOND_PARAMETER_INDEX);
  }

  /***
   * @return param1
   */
  protected boolean getParam1() {
    return param1;
  }

  /***
   * @return param2
   */
  protected boolean getParam2() {
    return param2;
  }
}
