package slogo.command.control;

import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.model.World;

@Deprecated
public class WhileLoop extends Control {

  public static final int WHILE_LOOP_PARAMETER_NUMBER = 2;
  public static final int WHILE_LOOP_BODY_INDEX = 1;

  /***
   * Creates a Control Command that emulates a while loop
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public WhileLoop(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
    setParamNumber(WHILE_LOOP_PARAMETER_NUMBER);
  }

  /***
   *
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution() throws CommandException {
  }

  /***
   * Runs loop with given loop variables and body Command
   *
   * @return last value received from loop, 0.0 if nothing
   * @throws CommandException if command cannot be executed
   */
  @Override
  public Double run() throws CommandException {
    Double returnVal = 0d;
    while(evaluateExpression()) {
      returnVal = executeParameter(WHILE_LOOP_BODY_INDEX).returnVal();
    }
    return returnVal;
  }
}
