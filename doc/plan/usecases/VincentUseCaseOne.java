//user inputs math command, for example, adding two numbers

class Controller {
  public void step() {
    String commandString = Controller.getInput();
    try {
      Command command = Parse.parse(commandString); // commandString is a math var
      command.execute();
    } ...
  }
}

class Sum extends Operation {
  private Map<String, Object> userVars;
  private Double paramOne;
  private Double paramTwo;

  public UserCommand(List<Command> parameters) throws ... {
  ...
   this.paramOne = (Double) parameters.get(0).execute();
   this.paramTwo = (Double) parameters.get(1).execute();
   ...
  }
  public Object execute() {
    return paramOne + paramTwo;
  }
}