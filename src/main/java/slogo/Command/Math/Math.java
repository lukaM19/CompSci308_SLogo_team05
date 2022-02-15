package slogo.Command.Math;

import java.util.ArrayList;
import java.util.List;
import slogo.Command.Command;
import slogo.Command.Exceptions.WrongParameterNumberException;
import slogo.Command.Exceptions.WrongParameterTypeException;
import slogo.model.World;

public abstract class Math extends Command {
  protected List<Double> mathParams;

  /***
   * Creates a Command object that only has doubles as parameters for math
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
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

  /***
   * Gets a given parameter as a double representation
   *
   * @param i is the index in the list
   * @return the element at i in the list
   */
  protected Double getMathParam(int i) {
    return mathParams.get(i);
  }
}
