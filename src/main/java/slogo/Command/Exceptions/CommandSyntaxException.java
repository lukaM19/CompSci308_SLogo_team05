package slogo.Command.Exceptions;

public abstract class CommandSyntaxException extends Exception {
  public CommandSyntaxException(String errorMessage) {
    super(errorMessage);
  }
}
