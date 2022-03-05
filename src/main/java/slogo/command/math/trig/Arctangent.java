package slogo.command.math.trig;

import java.util.List;

import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.math.Function;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"ArcTangent"}, arguments = 1)
public class Arctangent extends Trig {

  /***
   * Creates a Function Command that returns atan(param)
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Arctangent(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * does atan(param)
   *
   * @return atan(param)
   */
  @Override
  public Double run() {
    return Math.atan(getParam());
  }
}
