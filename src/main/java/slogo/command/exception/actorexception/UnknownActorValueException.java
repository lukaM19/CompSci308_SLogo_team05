package slogo.command.exception.actorexception;

import slogo.command.exception.actorexception.ActorException;

public class UnknownActorValueException extends ActorException {

  /***
   * Creates an exception related to an unrecognized actor attribute
   *
   * @param errorMessage is the message to show
   */
  public UnknownActorValueException(String errorMessage) {
    super(errorMessage);
  }
}
