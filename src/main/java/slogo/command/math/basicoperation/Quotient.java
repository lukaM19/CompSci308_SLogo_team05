package slogo.command.math.basicoperation;

import java.util.List;

import slogo.command.general.Command;
import slogo.command.math.Operation;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"Quotient"}, arguments = 2)
public class Quotient extends Operation {

  /***
   * creates an Operation command that divides two values
   *
   * @param parameters - parameters for command
   */
  public Quotient(List<Command> parameters) {
    super(parameters);
  }

  /***
   * divides the two parameters
   *
   * @return quotient of parameters
   */
  @Override
  public Double run() {
    return param1 / param2;
  }
}
