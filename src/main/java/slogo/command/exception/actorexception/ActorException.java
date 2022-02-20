package slogo.command.exception.actorexception;

import slogo.command.exception.CommandException;

public abstract class ActorException extends CommandException {
  /***
   * Creates an exception related to actors
   *
   * @param errorMessage is the message to show
   */
  public ActorException(String errorMessage) {
    super(errorMessage);
  }
}
