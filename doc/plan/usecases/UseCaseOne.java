// The user types 'fd 50' in the command window, and sees the turtle move in the display window leaving a trail, and the command is added to the environment's history.

class Controller {
  public void step() {
    String commandString = Controller.getInput();
    MainView.addCommandHistory(commandString);
    try {
      Command command = Parse.parse(commandString);


      List<MoveInfo> result = Model.executeCommand(command);

      MainView.updateTurtleScreen(result);
      ...
    } catch(Exception e) {
      View.showError();
    }
  }
}

class MainView {
  public void updateTurtleScreen(List<Move> moveList) {
    TurtleScreen.moveTurtles(moveList)
  }
}


