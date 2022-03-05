package slogo.command.math.basicoperation;

import java.util.List;

import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.math.Function;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"Minus"}, arguments = 1)
public class Minus extends Function {

  public static final double NEGATIVE_SIGN = -1.0;

  /***
   * Creates a Function Command that negates a given parameter
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public Minus(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * negates the parameter
   *
   * @return negative of the parameter
   */
  @Override
  public Double run() {
    return NEGATIVE_SIGN * getParam();
  }
}
