package slogo.command.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.model.World;

public abstract class Math extends Command {
  protected List<Double> mathParams;

  /***
   * Creates a Command object that only has doubles as parameters for math
   *
   * @param parameters - parameters for command
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Math(List<Command> parameters) throws WrongParameterTypeException {
    super(parameters);
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

  /***
   * Initializes math parameter list
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws WrongParameterTypeException if parameter number is wrong
   */
  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars)
      throws WrongParameterTypeException {
    mathParams = new ArrayList<>();
    for(int i=0; i<getParametersSize(); i++) {
      try {
        mathParams.add(executeParameter(i, world, userVars).returnVal());
      } catch(Exception e) {
        throw new WrongParameterTypeException(getCommandName() + mathParams.get(mathParams.size() - 1));
      }
    }
  }
}
