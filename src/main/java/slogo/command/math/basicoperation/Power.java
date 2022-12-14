package slogo.command.math.basicoperation;

import java.util.List;

import slogo.command.general.Command;
import slogo.command.math.Operation;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"Power"}, arguments = 2)
public class Power extends Operation {

  /***
   * creates an Operation command that exponentiates two values
   *
   * @param parameters - parameters for command
   */
  public Power(List<Command> parameters) {
    super(parameters);
  }

  /***
   * exponentiates the two parameters
   *
   * @return exponent of parameters
   */
  @Override
  public Double run() {
    return Math.pow(getParam1(), getParam2());
  }
}
