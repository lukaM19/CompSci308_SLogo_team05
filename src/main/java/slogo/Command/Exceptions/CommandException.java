package slogo.Command.Exceptions;

public abstract class CommandException extends Exception {
  public CommandException(String errorMessage) {
    super(errorMessage);
  }
}
