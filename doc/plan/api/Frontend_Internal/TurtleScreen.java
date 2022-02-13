/**
 * the class which manages the canvas on which the turtle moves, and also is resopnisble for visualizing
 * all the moves. Also controls the timeline of the animation, but that for now is done internally.
 */
public interface TurtleScreen extends canvas {

  /**
   * the main method which is only used in the package, it goes through the list of moves and makes
   * the appropiate actions.
   * @param moves the list of Move object according to which turtles should move/draw lines
   */
  public void moveTurtles(List<Move> moves){

  }

  /**
   * Clears the canvas
   */
  public void clearCanvas(){}

  /**
   * sets the new color of the canvas, selected by the user.
   * @param color the color selected by the user
   */
  public void setColor(Color color){}

}