//user mispells variable name

class Controller {
  public void step() {
    String commandString = Controller.getInput();
    try {
      Command command = Parse.parse(commandString); // commandString is the mispelled name of the user variable, fails
      ...
    } catch(ValueNotFoundException e) {
      View.showError(e);
    }
  }
}

class GenericValue extends Command {
  private Map<String, Object> userVars;
  private Object varValue;

  public UserCommand(List<Command> parameters) throws ValueNotFoundException {
    ...
    if(!userVars.contains(parameters.get(0))) {
      throw ValueNotFoundException(this.getClass().getSimpleName(), parameters.get(0));
    }
    ...
  }
}