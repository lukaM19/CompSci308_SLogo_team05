package slogo.command.math.trig;

import java.util.List;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterException;
import slogo.command.general.Command;
import slogo.command.math.Function;

public abstract class Trig extends Function {

  private double paramConverted;

  /***
   * Creates a Math Command that is trig related (must convert deg to rad)
   *
   * @param parameters - parameters for command
   */
  public Trig(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Converts deg to rad
   *
   * @throws WrongParameterTypeException if wrong parameters given
   * @throws WrongParameterNumberException if wrong number of parameters
   * @throws ImpliedParameterException if implied parameters missing
   */
  @Override
  protected void setUpExecution()
      throws WrongParameterTypeException, WrongParameterNumberException, ImpliedParameterException {
    super.setUpExecution();
    paramConverted = Math.toRadians(getParam());
  }

  @Override
  protected double getParam() {
    return paramConverted;
  }
}
