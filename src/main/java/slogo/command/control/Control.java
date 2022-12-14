package slogo.command.control;

import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.logic.Logic;
import slogo.model.World;

public abstract class Control extends Command {

  public static final int EXPRESSION_INDEX = 0;
  public static final int CONTROL_MIN_PARAMETER_NUMBER = 2;

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
    setMinParamNumber(CONTROL_MIN_PARAMETER_NUMBER);
  }

  /***
   * Evaluates if the expression Command is true or false
   *
   * @return true if expression is true, false otherwise
   * @throws WrongParameterTypeException if expression.execute() cannot be converted to a boolean
   */
  protected boolean evaluateExpression()
          throws CommandException {
    Double expressionResult = executeParameter(EXPRESSION_INDEX).returnVal();

    return Logic.ACCEPTED_VALUES.get(expressionResult != DEFAULT_VALUE);
  }

  /***
   * Checkes to make sure parameter condition is met
   *
   * @throws CommandException if parameter condition isn't reached
   */
  @Override
  protected void setUpExecution() throws CommandException {
  }
}
