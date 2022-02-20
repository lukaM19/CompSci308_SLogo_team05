package slogo.command.control;

import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.CommandResult;
import slogo.command.logic.Logic;
import slogo.command.general.Command;
import slogo.model.World;

public class WhileLoop extends Control {

  public static final int WHILE_LOOP_PARAMETER_NUMBER = 2;
  public static final int WHILE_LOOP_BODY_INDEX = 1;

  protected World world;
  protected Map<String, Double> userVars;

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
  }

  /***
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
    checkForExactParameterLength(WHILE_LOOP_PARAMETER_NUMBER);
    this.world = world;
    this.userVars = userVars;
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
    while(evaluateExpression(world, userVars)) {
      returnVal = executeParameter(WHILE_LOOP_BODY_INDEX, world, userVars).returnVal();
    }
    return returnVal;
  }
}
