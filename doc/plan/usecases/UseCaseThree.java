//The user sets a variable's value and sees it updated in the UI's Variable view.

class Controller {
  public void step() {
    String commandString = Controller.getInput();
    try {
      Command command = Parse.parse(commandString); // commandString is a user var
      command.execute();
    } ...
  }
}

class UserCommand extends Command {
  private Map<String, Object> userVars;
  private String varName;
  private Object varValue;

  public UserCommand(List<Command> parameters) throws ... {
   this.varName = parameters.get(0).execute().toString();
   this.varValue = parameters.get(1).execute();
  }
  public Object execute() {
    userVars.put(varName, varValue);
    return 1.0;
  }
}
