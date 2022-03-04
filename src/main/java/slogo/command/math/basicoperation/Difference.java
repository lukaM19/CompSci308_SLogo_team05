package slogo.command.math.basicoperation;

import java.util.List;

import slogo.command.general.Command;
import slogo.command.math.Operation;

@Deprecated
public class Difference extends Operation {
  /***
   * Creates an Operation command that subtracts two values
   *
   * @param parameters - parameters for command
   */
  public Difference(List<Command> parameters) {
    super(parameters);
  }

  /***
   * subtracts the two parameters
   *
   * @return difference of parameters
   */
  @Override
  public Double run() {
    return getParam1() - getParam2();
  }
}
