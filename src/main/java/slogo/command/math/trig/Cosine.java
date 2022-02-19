package slogo.command.math.trig;

import java.util.List;
import java.util.Optional;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.command.math.Function;

public class Cosine extends Function {

  /***
   * Creates a Function Command that returns cos(param)
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Cosine(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * does cos(param)
   *
   * @return cos(param)
   */
  @Override
  public CommandResult run() {
    return new CommandResult(java.lang.Math.cos(param), Optional.empty());
  }
}
