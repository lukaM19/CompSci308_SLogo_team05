package slogo.Command.Exceptions;

public class WrongParameterTypeException extends CommandSyntaxException{
  public WrongParameterTypeException(String errorMessage) {
    super(errorMessage);
  }
}
