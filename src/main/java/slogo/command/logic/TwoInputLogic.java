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

  protected boolean param1;
  protected boolean param2;

  /***
   * Creates a Logic Command that only takes two inputs
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public TwoInputLogic(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * Makes params private instance variables
   *
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution() throws CommandException {
    super.setUpExecution();
    checkForExactParameterLength(TIL_PARAMETER_NUMBER);
    param1 = evaluatedCommands.get(FIRST_PARAMETER_INDEX);
    param2 = evaluatedCommands.get(SECOND_PARAMETER_INDEX);
  }
}
