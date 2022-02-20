package slogo.command.exception.actorexception;

public class ActorNotFoundException extends ActorException {
  /***
   * Creates an exception related to an unrecognized actor
   *
   * @param errorMessage is the message to show
   */
  public ActorNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
