/***
 * Purpose
 *   The Logic abstract class creates an extendable foundation for all logic commands. It
 *    essentially evaluates each parameter command and converts them into booleans.
 * Design
 *   This is a well-designed class because it provides an extendable platform for many
 *    implementations of logic commands. Additionally, it pulls out code needed for converting
 *    every parameter into a boolean (which would be repeated through And, Not, and Or if not for
 *    this class).
 * Git links
 *  https://coursework.cs.duke.edu/compsci308_2022spring/slogo_team05/-/commit/409813025790dfd1cf00e0e07b1960da4240aa65
 *  https://coursework.cs.duke.edu/compsci308_2022spring/slogo_team05/-/commit/165456b4df16822257064353b3d403efafe7a94b
 *  https://coursework.cs.duke.edu/compsci308_2022spring/slogo_team05/-/commit/4d555291423f94c8f30cf22bf549b186dfc2ac67
 *  https://coursework.cs.duke.edu/compsci308_2022spring/slogo_team05/-/commit/e7eb716ffd21ab64b050b6cc51b30759910638ee
 */
package slogo.command.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterTypeException;

public abstract class Logic extends Command {

  public static final Map<Object, Boolean> ACCEPTED_VALUES = new HashMap<>(){{
    put(0.0, false);
    put(1.0, true);
    put(true, true);
    put(false, false);
  }};
  public static final Map<Boolean, Double> RETURN_VALUES = new HashMap<>(){{
    put(false, 0.0);
    put(true, 1.0);
  }};

  private List<Boolean> evaluatedCommands;

  /***
   * Creates a Command that evaluates boolean expressions
   *
   * @param parameters - parameters for command
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Logic(List<Command> parameters) throws WrongParameterTypeException {
    super(parameters);
  }

  /***
   * Makes sure given parameters can be evaluated as booleans
   *
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution() throws CommandException {
    evaluatedCommands = new ArrayList<>();
    for(int i = 0; i < getParametersSize(); i++) {
      Double executedValue = executeParameter(i).returnVal();
      evaluatedCommands.add(executedValue != DEFAULT_VALUE);
    }
  }

  /***
   * Checks if a given Object can be converted to a boolean
   *
   * @param executedValue to check
   * @return if the given Object is represented in the acceptedValues map
   */
  private boolean acceptableValue(Object executedValue) {
    return ACCEPTED_VALUES.containsKey(executedValue);
  }

  /***
   * @return evalutedCommands
   */
  protected List<Boolean> getEvaluatedCommands() {
    return evaluatedCommands;
  }
}
