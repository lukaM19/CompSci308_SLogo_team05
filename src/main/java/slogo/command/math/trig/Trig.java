package slogo.command.math.trig;

import java.util.List;
import java.util.Map;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterException;
import slogo.command.general.Command;
import slogo.command.math.Function;
import slogo.model.World;

public abstract class Trig extends Function {

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
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws WrongParameterTypeException if wrong parameters given
   * @throws WrongParameterNumberException if wrong number of parameters
   * @throws ImpliedParameterException if implied parameters missing
   */
  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars)
      throws WrongParameterTypeException, WrongParameterNumberException, ImpliedParameterException {
    super.setUpExecution(world, userVars);
    param = Math.toRadians(param);
  }
}
