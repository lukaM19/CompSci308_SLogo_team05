package slogo.command.exception.worldexception;

import slogo.command.exception.actorexception.ActorException;

public class UnknownWorldValueException extends ActorException {

  /***
   * Creates an exception related to an unrecognized actor attribute
   *
   * @param errorMessage is the message to show
   */
  public UnknownWorldValueException(String errorMessage) {
    super(errorMessage);
  }
}
