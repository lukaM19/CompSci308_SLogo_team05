package slogo.command.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import slogo.command.general.Command;
import slogo.command.exception.WrongParameterTypeException;
import slogo.model.World;

public abstract class Math extends Command {
  protected List<Double> mathParams;

  /***
   * Creates a Command object that only has doubles as parameters for math
   *
   * @param world - model to execute on
   * @param parameters - parameters for command
   * @param userVars - the map of user variables
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Math(World world, List<Command> parameters, Map<String, Object> userVars) throws WrongParameterTypeException {
    super(world, parameters, userVars);
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
