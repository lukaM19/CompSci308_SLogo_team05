package slogo.Command.Math;

import java.util.ArrayList;
import java.util.List;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterTypeException;

public abstract class Math extends Command {
  private List<Double> mathParams;

  public Math(List<Command> parameters) throws WrongParameterTypeException {
    super(parameters);
    mathParams = new ArrayList<>();
    for(int i=0; i<parameters.size(); i++) {
      try {
        Command currentCommand = parameters.get(i);
        mathParams.add((Double) currentCommand.execute());
      } catch(Exception e) {
        throw new WrongParameterTypeException(getCommandName() + parameters
            .get(i));
      }
    }
  }

  protected Double getParam(int i) {
    return mathParams.get(i);
  }
}
