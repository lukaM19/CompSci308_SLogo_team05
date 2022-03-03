package slogo.command.math.basicoperation;

import java.util.List;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.general.Command;
import slogo.command.math.Function;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"SquareRoot"}, arguments = 1)
public class SquareRoot extends Function {

  /***
   * creates an Operation command that exponentiates two values
   *
   * @param parameters - parameters for command
   */
  public SquareRoot(List<Command> parameters) throws WrongParameterNumberException {
    super(parameters);
  }

  /***
   * exponentiates the two parameters
   *
   * @return exponent of parameters
   */
  @Override
  public Double run() {
    return Math.sqrt(param);
  }
}
