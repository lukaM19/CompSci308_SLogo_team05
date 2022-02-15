package slogo.command.control;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.logic.Logic;
import slogo.command.general.Command;
import slogo.model.World;

public class Conditional extends Control {

  public static final int CONDITIONAL_BODY_NUMBER = 2;
  public static final int CONDITIONAL_BODY_ONE_INDEX = 0;
  public static final int CONDITIONAL_BODY_TWO_INDEX = 1;

  private boolean expressionEvaluation;

  /***
   * Creates a Control Command that evaluates commands based on if the given expr is true or false
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Conditional(World world, List<Command> parameters, Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters, userVars);
    if(bodyCommands.size() != CONDITIONAL_BODY_NUMBER) {
      throw new WrongParameterNumberException(getCommandName() + bodyCommands.size());
    }
   expressionEvaluation = evaluateExpression();
  }

  /***
   * Evaluates if the expression Command is true or false
   *
   * @return true if expression is true, false otherwise
   * @throws WrongParameterTypeException if expression.execute() cannot be converted to a boolean
   */
  private boolean evaluateExpression() throws WrongParameterTypeException {
    Object result = expression.execute();
    if(Logic.acceptedValues.containsKey(result)) {
      return Logic.acceptedValues.get(result);
    }
    throw new WrongParameterTypeException(getCommandName() + result);
  }

  /***
   * Runs the corresponding Command based on what expression evaluated to
   *
   * @return result of executing the correct Command
   */
  @Override
  public Object execute() {
    if(expressionEvaluation) {
      return bodyCommands.get(CONDITIONAL_BODY_ONE_INDEX).execute();
    }
    else {
      return bodyCommands.get(CONDITIONAL_BODY_TWO_INDEX).execute();
    }
  }
}
