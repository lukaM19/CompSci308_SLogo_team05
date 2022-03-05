package slogo.command.math.random;

import java.util.List;

import slogo.command.general.Command;
import slogo.command.math.Operation;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"RandomRange"}, arguments = 2)
public class RandomRange extends Operation {

  private double lowerBound;
  private double upperBound;
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
    if(getParam1() > getParam2()) {
      lowerBound = getParam2();
      upperBound = getParam1();
    } else {
      lowerBound = getParam1();
      upperBound = getParam2();
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
    return Math.random() * (upperBound - lowerBound) + lowerBound;
  }
}
