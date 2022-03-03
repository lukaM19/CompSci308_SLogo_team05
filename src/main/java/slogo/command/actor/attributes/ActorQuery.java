package slogo.command.actor.attributes;

import static slogo.command.general.Command.VAR_NAME_KEY;
import static slogo.model.Actor.HEADING_KEY;
import static slogo.model.Actor.VISIBILITY_KEY;
import static slogo.model.Actor.X_COR_KEY;
import static slogo.model.Actor.Y_COR_KEY;
import static slogo.model.Turtle.PEN_COLOR_KEY;
import static slogo.model.Turtle.PEN_KEY;
import static slogo.model.Turtle.SHAPE_KEY;

import java.util.List;
import slogo.command.actor.ActorCommand;
import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.exception.actorexception.UnknownActorValueException;
import slogo.model.Actor;
import slogo.parser.ImpliedArgument;
import slogo.parser.SlogoCommand;

@SlogoCommand(keywords = {"XCoordinate", "YCoordinate", "Heading", "IsPenDown", "IsShowing", "PenColor", "Shape"})
@ImpliedArgument(keywords = {"XCoordinate"}, arg = VAR_NAME_KEY, value = X_COR_KEY)
@ImpliedArgument(keywords = {"YCoordinate"}, arg = VAR_NAME_KEY, value = Y_COR_KEY)
@ImpliedArgument(keywords = {"Heading"}, arg = VAR_NAME_KEY, value = HEADING_KEY)
@ImpliedArgument(keywords = {"IsPenDown"}, arg = VAR_NAME_KEY, value = PEN_KEY)
@ImpliedArgument(keywords = {"IsShowing"}, arg = VAR_NAME_KEY, value = VISIBILITY_KEY)
@ImpliedArgument(keywords = {"PenColor"}, arg = VAR_NAME_KEY, value = PEN_COLOR_KEY)
@ImpliedArgument(keywords = {"Shape"}, arg = VAR_NAME_KEY, value = SHAPE_KEY)

public class ActorQuery extends ActorCommand {

  private String queryVar;
  private Actor actor;

  /***
   * Creates a Command that gets a given attribute from the actor
   *
   * @param parameters - parameters for command
   */
  public ActorQuery(List<Command> parameters) {
    super(parameters);
  }

  /***
   * Sets up the variable to get
   *
   * @throws CommandException if command cannot be executed
   */
  @Override
  protected void setUpExecution() throws CommandException {
    super.setUpExecution();
    queryVar = getImpliedParameter(VAR_NAME_KEY);
    actor = actors.get(actors.size()-1);
    if(!actor.hasVal(queryVar)) {
      throw new UnknownActorValueException(getCommandName() + queryVar);
    }
  }

  /***
   * Returns the desired actor parameter, .get() will always be successful because it was checked
   * previously.
   *
   * @return the desired actor parameter
   */
  @Override
  public Double run() {
    return actor.getVal(queryVar);
  }
}
