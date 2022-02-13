// The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.

class Controller {
  public void step() {
    String commandString = Controller.getInput();
    try {
      Command command = Parse.parse(commandString); // failed
      ...
    } catch(Exception e) {
      View.showError(); // sends off information to view
    }
  }
}


