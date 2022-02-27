package slogo.command.actorcommand.move.absolute;

import static slogo.command.actorcommand.ActorCommand.ACTOR_ID_KEY;

import java.util.List;
import slogo.command.actorcommand.move.Move;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.value.GenericValue;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"ClearScreen"})
@ImpliedArgument(keywords = {"ClearScreen"}, arg = ACTOR_ID_KEY, value = "0")

public class ClearScreen extends Move {
  
  private Home moveCommand;

  /***
   * Creates command that returns the turtle home
   *
   * @param parameters - parameters for command
   */
  public ClearScreen(
      List<Command> parameters) {
    super(parameters);
  }

  /***
   * Clears screen and moves turtle home
   *
   * @return distance travelled
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected Double run() throws CommandException {
    //TODO: CLEAR TURTLE TRAILS 
    return executeInstanceCommand(moveCommand);
  }

  /***
   * Creates new home command
   */
  @Override
  protected void calculateMovement() {
    moveCommand = new Home(List.of());
    moveCommand.setImpliedParameters(impliedParameters);
  }

}
