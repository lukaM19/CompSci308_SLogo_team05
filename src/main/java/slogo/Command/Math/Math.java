package slogo.Command.Math;

import java.util.ArrayList;
import java.util.List;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterTypeException;
import slogo.model.World;

public abstract class Math extends Command {
  private List<Double> mathParams;

  public Math(World world, List<Command> parameters) throws WrongParameterTypeException {
    super(world, parameters);
    mathParams = new ArrayList<>();
    for(int i=0; i<getParametersSize(); i++) {
      try {
        Command currentCommand = this.parameters.get(i);
        mathParams.add((Double) currentCommand.execute());
      } catch(Exception e) {
        throw new WrongParameterTypeException(getCommandName() + this.parameters
            .get(i));
      }
    }
  }

  protected Double getParam(int i) {
    return mathParams.get(i);
  }
}
