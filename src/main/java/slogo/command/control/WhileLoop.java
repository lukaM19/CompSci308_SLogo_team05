package slogo.command.control;

import java.util.List;
import java.util.Map;
import slogo.command.exception.WrongParameterNumberException;
import slogo.command.exception.WrongParameterTypeException;
import slogo.command.logic.Logic;
import slogo.command.util.Command;
import slogo.model.World;

public class WhileLoop extends Control {

  public static final int WHILE_LOOP_PARAMETER_NUMBER = 1;
  public static final int WHILE_LOOP_BODY_INDEX = 0;

  private Command body;

  /***
   * Creates a Control Command that emulates a while loop
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public WhileLoop(World world, List<Command> parameters,
      Map<String, Object> userVars)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(world, parameters, userVars);
    checkForExactParameterLength(WHILE_LOOP_PARAMETER_NUMBER);
    body = this.parameters.get(WHILE_LOOP_BODY_INDEX);
  }

  /***
   * Runs loop with given loop variables and body Command
   *
   * @return last value received from loop, 0.0 if nothing
   */
  @Override
  public Object execute() {
    Object exprResult = expression.execute();
    Object returnVal = 0.0;

    while(Logic.acceptedValues.containsKey(exprResult) && Logic.acceptedValues.get(exprResult)) {
      returnVal = body.execute();
      exprResult = expression.execute();
    }
    return returnVal;
  }
}
