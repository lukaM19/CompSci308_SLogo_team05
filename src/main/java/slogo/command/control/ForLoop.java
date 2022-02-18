package slogo.command.control;

import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.model.World;

public class ForLoop extends WhileLoop {

  protected String counterKey;
  protected int counter;
  protected int increment;
  protected int limit;

  /***
   * Creates a Control Command that represents a for loop a given amount of times
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public ForLoop(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * Assigns loop variables
   *
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  private void assignLoopVariables(World world, Map<String, Object> userVars)
      throws CommandException {
    Object exprVal = expression.execute(world, userVars);
    try {
      ForLoopParameters forLoopParameters = (ForLoopParameters) exprVal;
      counterKey = forLoopParameters.counterName();
      counter = forLoopParameters.counter();
      limit = forLoopParameters.limit();
      increment = forLoopParameters.increment();
    } catch (Exception e) {
      throw new WrongParameterTypeException(getCommandName() + exprVal);
    }
  }

  /***
   * Creates a new Command object that evaluates whether the for loop conditions are met
   *
   * @return new Command object that emulates for loop
   */
  private Command getForLoopExpression() {
    return new Command(parameters) {
      @Override
      protected void setUpExecution(World world, Map<String, Object> userVars) {}

      @Override
      public Object run() {
        counter+=increment;
        userVars.put(counterKey, counter);
        return counter < limit;
      }
    };
  }

  /***
   * Sets up loop variables and loop expression
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution(World world, Map<String, Object> userVars) throws CommandException {
    super.setUpExecution(world, userVars);
    assignLoopVariables(world, userVars);
    expression = getForLoopExpression();
  }
}
