/***
 * Purpose
 *   The And class provides a way to use the && operation in Slogo.
 * Design
 *   This is a well-designed class because it contains no code that is repeated elsewhere in any
 *    other command. This is mostly because the superclasses:  Logic, TwoInputLogic pulled out
 *    code that is common to all commands. Therefore, this class only needed to implement one
 *    function: run().
 * Git links
 *  https://coursework.cs.duke.edu/compsci308_2022spring/slogo_team05/-/commit/165456b4df16822257064353b3d403efafe7a94b
 *  https://coursework.cs.duke.edu/compsci308_2022spring/slogo_team05/-/commit/4d555291423f94c8f30cf22bf549b186dfc2ac67
 */
package slogo.command.logic.operator;

import java.util.List;
import slogo.command.general.Command;
import slogo.command.exception.parameterexception.WrongParameterNumberException;
import slogo.command.exception.parameterexception.WrongParameterTypeException;
import slogo.command.logic.TwoInputLogic;
import slogo.parser.annotations.SlogoCommand;

@SlogoCommand(keywords = {"And"}, arguments = 2)
public class And extends TwoInputLogic {

  /***
   * Creates a Logic Command that takes two inputs and ANDs them
   *
   * @param parameters - parameters for command
   * @throws WrongParameterNumberException if too many/few parameters
   * @throws WrongParameterTypeException if parameters have incorrect type
   */
  public And(List<Command> parameters)
      throws WrongParameterNumberException, WrongParameterTypeException {
    super(parameters);
  }

  /***
   * Performs AND operation on the parameters
   *
   * @return corresponding double to true/false
   */
  @Override
  public Double run() {
    return RETURN_VALUES.get(getParam1() && getParam2());
  }
}
