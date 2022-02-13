//user inputs command with wrong number of parameters

class Controller {
  public void step() {
    String commandString = Controller.getInput();
    try {
      Command command = Parse.parse(commandString); // fails
      ...
    } catch(ParameterMisMatchException e) {
      View.showError(e);
    }
  }
}

class UserCommand extends Command {
  private Map<String, Object> userVars;
  private String varName;
  private Object varValue;

  public UserCommand(List<Command> parameters) throws ParameterMismatchException {
    if(parameters.size() != 2) {
      throw ParameterMisMatchException(this.getClass().getSimpleName(), parameters.size());
    }
    ...
  }
}