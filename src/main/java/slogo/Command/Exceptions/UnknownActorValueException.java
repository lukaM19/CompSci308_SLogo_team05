package slogo.Command.Exceptions;

public class UnknownActorValueException extends CommandException {
  public UnknownActorValueException(String errorMessage) {
    super(errorMessage);
  }
}
