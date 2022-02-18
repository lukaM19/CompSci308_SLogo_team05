package slogo.command.control;

import java.util.List;
import java.util.Map;
import slogo.command.exception.ActorNotFoundException;
import slogo.command.exception.CommandException;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.logic.Logic;
import slogo.command.general.Command;
import slogo.model.World;

public class WhileLoop extends Control {

  public static final int WHILE_LOOP_PARAMETER_NUMBER = 1;
  public static final int WHILE_LOOP_BODY_INDEX = 0;

  private Command body;
  protected World world;
  protected Map<String, Object> userVars;

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
    checkForExactParameterLength(WHILE_LOOP_PARAMETER_NUMBER);
  }

  /***
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Object> userVars) throws CommandException {
    super.setUpExecution(world, userVars);
    body = this.parameters.get(WHILE_LOOP_BODY_INDEX);
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
  public Object run() throws CommandException {
    Object exprResult = expression.execute(world, userVars);
    Object returnVal = DEFAULT_VALUE;

    while(Logic.acceptedValues.containsKey(exprResult) && Logic.acceptedValues.get(exprResult)) {
      returnVal = body.execute(world, userVars);
      exprResult = expression.execute(world, userVars);
    }
    return returnVal;
  }
}
