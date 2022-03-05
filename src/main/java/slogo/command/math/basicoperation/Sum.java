package slogo.command.math.basicoperation;

import static slogo.command.actorcommand.ActorCommand.SCALE_KEY;

import java.util.List;
import java.util.Map;

import slogo.command.exception.parameterexception.impliedparameterexception.ImpliedParameterException;
import slogo.command.exception.parameterexception.impliedparameterexception.WrongImpliedParameterTypeException;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.math.Operation;
import slogo.model.World;
import slogo.parser.annotations.ImpliedArgument;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"Sum", "Difference"}, arguments = 2)
@ImpliedArgument(keywords =  {"Sum"}, arg = SCALE_KEY, value = "1")
@ImpliedArgument(keywords =  {"Difference"}, arg = SCALE_KEY, value = "-1")
public class Sum extends Operation {

  /***
   * Creates an Operation command that adds two values
   *
   * @param parameters - parameters for command
   */
  public Sum(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Sets up implied parameters
   *
   * @param world - the model to execute on
   * @param userVars - the map of user variables
   * @throws WrongParameterTypeException if wrong parameter type
   * @throws WrongParameterNumberException if wrong number of parameters
   */
  @Override
  protected void setUpExecution(World world, Map<String, Double> userVars)
      throws WrongParameterTypeException, WrongParameterNumberException, ImpliedParameterException {
    super.setUpExecution(world, userVars);
    try {
      param2 *= Double.parseDouble(getImpliedParameter(SCALE_KEY));
    } catch (NumberFormatException e) {
      throw new WrongImpliedParameterTypeException(getCommandName() + getImpliedParameter(SCALE_KEY));
    }
  }

  /***
   * adds the two parameters
   *
   * @return sum of parameters
   */
  @Override
  public Double run() {
    return param1 + param2;
  }
}
