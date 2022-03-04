package slogo.command.math.constants;

import java.util.List;

import slogo.command.general.Command;
import slogo.command.math.Math;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"Pi"})
public class Pi extends Math {

  /***
   * Creates a Command object that only has doubles as parameters for math
   *
   * @param parameters - parameters for command
   */
  public Pi(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Returns pi
   *
   * @return pi
   */
  @Override
  protected Double run() {
    return java.lang.Math.PI;
  }
}
