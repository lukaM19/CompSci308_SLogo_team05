package slogo.command.control;

import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.model.World;

public abstract class Control extends Command {

  public static final int EXPRESSION_INDEX = 0;
  public static final int CONTROL_MIN_PARAMETER_NUMBER = 2;

  protected Command expression;
  protected List<Command> bodyCommands;

  /***
   * Creates a Command that evaluates given commands based on a Command expression
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Control(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
    checkForMinParameterLength(CONTROL_MIN_PARAMETER_NUMBER);
  }

  /***
   * Sets up expression instance variable and body command list
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
    expression = this.parameters.get(EXPRESSION_INDEX);
    this.parameters.remove(EXPRESSION_INDEX);
    bodyCommands = this.parameters;
  }
}
