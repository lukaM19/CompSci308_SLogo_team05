package slogo.Command.Exceptions;

public class WrongParameterNumberException extends CommandSyntaxException{

  public WrongParameterNumberException(String errorMessage) {
    super(errorMessage);
  }
}
