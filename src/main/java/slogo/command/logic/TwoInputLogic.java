package slogo.command.logic;

import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.model.World;

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
    checkForExactParameterLength(TIL_PARAMETER_NUMBER);
  }

  /***
   * Makes params private instance variables
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
    super.setUpExecution(world, userVars);
    param1 = evaluatedCommands.get(FIRST_PARAMETER_INDEX);
    param2 = evaluatedCommands.get(SECOND_PARAMETER_INDEX);
  }
}
