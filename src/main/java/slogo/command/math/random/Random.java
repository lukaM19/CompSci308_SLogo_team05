package slogo.command.math.random;

import java.util.List;

import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.math.Function;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"Random"}, arguments = 1)
public class Random extends Function {

  /***
   * Creates a Function Command that returns a random value under the param
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Random(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * gets a random value under max value
   *
   * @return random value
   */
  @Override
  public Double run() {
    return Math.random() * param;
  }
}
