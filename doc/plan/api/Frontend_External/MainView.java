/**
 * The main method , which makes the main root for the scene, arranges all of the above elements
 * into the root. Manages some main and high-level GUI interactions. It also has the public methods
 * which are used to interact with the controller.
 */
public interface MainView {

  /**
   * A call for the GUI to actually display the move the turtle should make.
   *
   * @param moveList the list of moves which turtle needs to complete, with all the info necessary
   *                 to do so.
   */
  public void updateTurtleScreen(List<Move> moveList) {

  }

  /**
   * A method which creates a pop up window to display any given exception
   *
   * @param s the text for the exception
   */
  public void displayException(String s) {

  }

  /**
   * Add a new user defined variable to the VariableView display, called whenever the parser detects
   * (and tells controller) that user created a new custom variable.
   */
  public void addNewVariable() {

  }

  /**
   * Add a new user defined command to the UserFunctionView display, called whenever the parser
   * detects (and tells controller) that user created a new custom command.
   */
  public void addNewUserCommand() {

  }


  /**
   * sets the background color to a new color selected by the user
   *
   * @param color the new color selected by the user.
   */
  public void setBackgroundColor(Color color){

  }

  /**
   * @return all input that the user has made currently, in string form.
   */
  public String getInputText(){

  }

  /**
   * build and arrange all the UI elemnts into the main root.
   */
  private void buildUI() {

  }
}