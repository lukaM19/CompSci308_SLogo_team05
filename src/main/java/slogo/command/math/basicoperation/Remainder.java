package slogo.command.math.basicoperation;

import java.util.List;
import java.util.Optional;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.command.math.Operation;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"Remainder"}, arguments = 2)
public class Remainder extends Operation {

  /***
   * creates an Operation command that mods two values
   *
   * @param parameters - parameters for command
   */
  public Remainder(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Mods the two parameters
   *
   * @return remainder of parameters
   */
  @Override
  public Double run() {
    return getParam1() % getParam2();
  }
}
