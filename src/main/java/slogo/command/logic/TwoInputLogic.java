/***
 * Purpose
 *   The TwoInputLogic abstract class creates an extendable foundation for all logic commands that
 *    take two inputs. It essentially guarantees that the command is passed two parameters and
 *    sets up the two parameters as instance variables.
 * Design
 *   This is a well-designed class because it provides an extendable platform for many
 *    implementations of two-input logic commands. It also pulls out code that would be repeated
 *    across subclasses (i.e. checking for two parameters).
 * Git links
 *  https://coursework.cs.duke.edu/compsci308_2022spring/slogo_team05/-/commit/165456b4df16822257064353b3d403efafe7a94b
 *  https://coursework.cs.duke.edu/compsci308_2022spring/slogo_team05/-/commit/4d555291423f94c8f30cf22bf549b186dfc2ac67
 *  https://coursework.cs.duke.edu/compsci308_2022spring/slogo_team05/-/commit/fdbeaa415b2b8ee80d16e961a2d4209786185bf7
 */

package slogo.command.logic;

import java.util.List;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;

public abstract class TwoInputLogic extends Logic{

  public static final int TIL_PARAMETER_NUMBER = 2;
  public static final int FIRST_PARAMETER_INDEX = 0;
  public static final int SECOND_PARAMETER_INDEX = 1;

  private boolean param1;
  private boolean param2;

  /***
   * Creates a Logic Command that only takes two inputs
   *
   * @param parameters - parameters for command
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public TwoInputLogic(List<Command> parameters)
      throws WrongParameterTypeException {
    super(parameters);
    setParamNumber(TIL_PARAMETER_NUMBER);
  }

  /***
   * Makes params private instance variables
   *
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution() throws CommandException {
    super.setUpExecution();
    param1 = getEvaluatedCommands().get(FIRST_PARAMETER_INDEX);
    param2 = getEvaluatedCommands().get(SECOND_PARAMETER_INDEX);
  }

  /***
   * @return param1
   */
  protected boolean getParam1() {
    return param1;
  }

  /***
   * @return param2
   */
  protected boolean getParam2() {
    return param2;
  }
}
