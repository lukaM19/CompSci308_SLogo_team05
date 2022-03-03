package slogo.command.math.comparator;

import static slogo.command.logic.Logic.RETURN_VALUES;

import java.util.List;
import slogo.command.general.Command;
import slogo.command.math.Operation;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"NotEqual"}, arguments = 2)
public class NotEquals extends Operation {

  /***
   * Creates a Logic Command that takes two inputs and compares if the first does not equal the second
   *
   * @param parameters - parameters for command
   */
  public NotEquals(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Determines if first param does not equal the second
   *
   * @return corresponding double to true/false
   */
  public Double run() {
    return RETURN_VALUES.get(getParam1() != getParam2());
  }
}
