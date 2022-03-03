package slogo.command.math.basicoperation;

import java.util.List;
import java.util.Optional;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.command.general.CommandResult;
import slogo.command.math.Operation;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"Product"}, arguments = 2)
public class Product extends Operation {

  /***
   * creates an Operation command that multiplies two values
   *
   * @param parameters - parameters for command
   */
  public Product(List<Command> parameters) {
    super(parameters);
  }

  /***
   * multiplies the two parameters
   *
   * @return product of parameters
   */
  @Override
  public Double run() {
    return getParam1() * getParam2();
  }
}
