package slogo.command.math;

import java.util.ArrayList;
import java.util.List;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterException;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterTypeException;

public abstract class Math extends Command {
  protected List<Double> mathParams;

  /***
   * Creates a Command object that only has doubles as parameters for math
   *
   * @param parameters - parameters for command
   */
  public Math(List<Command> parameters) {
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
   * @throws WrongParameterTypeException if parameter number is wrong
   */
  @Override
  protected void setUpExecution()
      throws WrongParameterTypeException, WrongParameterNumberException, ImpliedParameterException {
    mathParams = new ArrayList<>();
    for(int i=0; i<getParametersSize(); i++) {
      try {
        mathParams.add(executeParameter(i).returnVal());
      } catch(Exception e) {
        throw new WrongParameterTypeException(getCommandName() + mathParams.get(mathParams.size() - 1));
      }
    }
  }
}
