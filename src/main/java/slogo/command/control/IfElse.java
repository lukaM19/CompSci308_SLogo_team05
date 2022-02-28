package slogo.command.control;

import slogo.command.exception.CommandException;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.general.Command;
import slogo.model.World;
import slogo.parser.SlogoCommand;

import java.util.List;
import java.util.Map;

@SlogoCommand(keywords = {"IfElse"}, arguments = 3)
public class IfElse extends If {

  public static final int IF_ELSE_PARAMETER_COUNT = 3;
  public static final int ELSE_BLOCK_INDEX = 2;

  /***
   * Creates a Control Command that evaluates commands based on if the given expr is true or false
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public IfElse(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  protected int paramCount() {
    return IF_ELSE_PARAMETER_COUNT;
  }

  protected Double elseBehavior() throws CommandException {
    return executeParameter(ELSE_BLOCK_INDEX, world, userVars).returnVal();
  }
}
