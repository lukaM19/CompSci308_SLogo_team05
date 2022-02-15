package slogo.Command.Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;
import slogo.Command.Math.Math;
import slogo.model.World;

public abstract class Logic extends Command {

  public static final HashMap<Object, Boolean> acceptedValues = new HashMap<>(){{
    put(0.0, false);
    put(1.0, true);
    put(true, true);
    put(false, false);
  }};
  public static final HashMap<Boolean, Double> returnValues = new HashMap<>(){{
    put(false, 0.0);
    put(true, 1.0);
  }};

  protected List<Boolean> evaluatedCommands;

  /***
   * Creates a Command that evaluates boolean expressions
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Logic(World world, List<Command> parameters) throws WrongParameterTypeException {
    super(world, parameters);
    checkEvaluatedParameters();
  }

  /***
   * Makes sure given parameters can be evaluated as booleans
   *
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  private void checkEvaluatedParameters() throws WrongParameterTypeException {
    evaluatedCommands = new ArrayList<>();
    for(Command command: this.parameters) {
      Object executedValue = command.execute();
      if(!acceptableValue(executedValue)) {
        throw new WrongParameterTypeException(getCommandName() + command);
      }
      evaluatedCommands.add(acceptedValues.get(executedValue));
    }
  }

  /***
   * Checks if a given Object can be converted to a boolean
   *
   * @param executedValue to check
   * @return if the given Object is represented in the acceptedValues map
   */
  private boolean acceptableValue(Object executedValue) {
    return acceptedValues.containsKey(executedValue);
  }
}
