package slogo.command.exception;

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
