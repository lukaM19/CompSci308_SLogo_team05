package slogo.command.math.random;

import java.util.List;

import slogo.command.general.Command;
import slogo.command.math.Operation;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"RandomRange"}, arguments = 2)
public class RandomRange extends Operation {

  /***
   * Creates an Operation command that returns a random value in the given range
   *
   * @param parameters - parameters for command
   */
  public RandomRange(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Makes param2 > param1 for proper execution
   */
  private void makeRangePositive() {
    if(param1 > param2) {
      double temp = param1;
      param1 = param2;
      param2 = temp;
    }
  }

  /***
   * gets a random value from the range
   *
   * @return random value
   */
  @Override
  public Double run() {
    makeRangePositive();
    return Math.random() * (param2 - param1) + param1;
  }
}
