package slogo.command.control;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.util.Command;
import slogo.model.World;

public abstract class Control extends Command {

  public static final int EXPRESSION_INDEX = 0;
  public static final int CONTROL_MIN_PARAMETER_NUMBER = 2;

  protected Command expression;
  protected List<Command> bodyCommands;

  /***
   * Creates a Command that evaluates given commands based on a Command expression
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Control(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters, userVars);
    checkForMinParameterLength(CONTROL_MIN_PARAMETER_NUMBER);
    expression = this.parameters.get(EXPRESSION_INDEX);
    this.parameters.remove(EXPRESSION_INDEX);
    bodyCommands = this.parameters;
  }
}
