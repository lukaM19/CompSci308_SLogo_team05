package slogo.Command.Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterTypeException;
import slogo.Command.Math.Math;
import slogo.model.World;

public abstract class Logic extends Command {

  public static final HashMap<Object, Boolean> acceptedValues = new HashMap<>(){{
    put(0.0, false);
    put(1.0, true);
  }};

  public static final HashMap<Boolean, Double> returnValues = new HashMap<>(){{
    put(false, 0.0);
    put(true, 1.0);
  }};

  protected List<Boolean> evaluatedCommands;

  public Logic(World world, List<Command> parameters) throws WrongParameterTypeException {
    super(world, parameters);
    checkEvaluatedParameters();
  }

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

  private boolean acceptableValue(Object executedValue) {
    return acceptedValues.containsKey(executedValue);
  }
}
