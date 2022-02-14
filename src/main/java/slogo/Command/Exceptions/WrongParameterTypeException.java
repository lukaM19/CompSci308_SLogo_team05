package slogo.Command.Exceptions;

public class WrongParameterTypeException extends CommandException {
  public WrongParameterTypeException(String errorMessage) {
    super(errorMessage);
  }
}
