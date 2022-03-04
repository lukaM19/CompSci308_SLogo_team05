package slogo.command.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
      Double executedValue = executeParameter(i, world, userVars).returnVal();
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
