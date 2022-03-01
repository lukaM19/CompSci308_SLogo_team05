package slogo.command.control;

import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.model.World;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"If"}, arguments = 2)
public class If extends Control {

  public static final int IF_PARAMETER_COUNT = 2;
  public static final int IF_BLOCK_INDEX = 1;

  protected World world;
  protected Map<String, Double> userVars;

  /***
   * Creates a Control Command that evaluates commands if the given expr is true
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public If(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  protected int paramCount() {
    return IF_PARAMETER_COUNT;
  }

  /***
   * Sets up if statement header evaluation and checks for body size
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
    super.setUpExecution(world, userVars);
    checkForExactParameterLength(paramCount());
    this.world = world;
    this.userVars = userVars;
  }

  /***
   * Runs the corresponding Command based on what expression evaluated to
   *
   * @return result of executing the correct Command
   * @throws CommandException if command cannot be executed
   */
  @Override
  public Double run() throws CommandException {
    if(evaluateExpression(world, userVars)) {
      return executeParameter(IF_BLOCK_INDEX, world, userVars).returnVal();
    } else {
      return elseBehavior();
    }
  }

  /**
   * What to do if the expression evaluates to false.
   * Mainly exists to allow IfElse to override this behavior.
   * @return the result if the expression is false
   * @throws CommandException if any CommandExceptions are thrown in the process
   */
  protected Double elseBehavior() throws CommandException {
    return DEFAULT_VALUE;
  }
}
