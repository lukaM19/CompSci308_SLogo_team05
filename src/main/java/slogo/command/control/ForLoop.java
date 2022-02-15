package slogo.command.control;

import java.util.List;
import java.util.Map;
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
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public ForLoop(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters, userVars);
    assignLoopVariables();
    expression = getForLoopExpression();
  }

  /***
   * Assigns loop variables
   *
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  private void assignLoopVariables() throws WrongParameterTypeException {
    Object exprVal = expression.execute();
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
   * @return new Command object
   */
  private Command getForLoopExpression() {
    return new Command(world, parameters, userVars) {
      @Override
      public Object execute() {
        counter+=increment;
        userVars.put(counterKey, counter);
        return counter < limit;
      }
    };
  }
}
