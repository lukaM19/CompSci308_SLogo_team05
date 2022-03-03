package slogo.command.math.comparator;

import static slogo.command.logic.Logic.RETURN_VALUES;

import java.util.List;
import slogo.command.general.Command;
import slogo.command.math.Operation;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"LessEqual"}, arguments = 2)
public class LessEqual extends Operation {

  /***
   * Creates a Logic Command that takes two inputs and compares if the first is less than or equal to the second
   *
   * @param parameters - parameters for command
   */
  public LessEqual(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Determines if first param is less than or equal to the second
   *
   * @return corresponding double to true/false
   */
  @Override
  public Double run() {
    return RETURN_VALUES.get(getParam1() <= getParam2());
  }
}
