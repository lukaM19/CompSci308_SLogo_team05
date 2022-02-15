package slogo.Command.Logic;

import java.util.List;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;
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
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public TwoInputLogic(World world, List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters);
    checkForCorrectParameterLength(TIL_PARAMETER_NUMBER);
    param1 = evaluatedCommands.get(FIRST_PARAMETER_INDEX);
    param2 = evaluatedCommands.get(SECOND_PARAMETER_INDEX);
  }
}
