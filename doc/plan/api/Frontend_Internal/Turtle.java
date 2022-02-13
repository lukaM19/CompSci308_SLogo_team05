/**
 * Turtle is the graphical object(Node) which is being used to animate the movements of the turtle,
 * handles stuff like assigning an image to the turtle etc.
 */
public interface Turtle {

  /**
   * @param image new image which should repalce the default turtle
   */
  public setDesign(Image image){

  }

  /**
   * sets the direction of the turtle so it's facing the right way.
   * @param by degree what angle the turtle should turn.
   */
  public setDirection(double degree){

  }

  /**
   *New pen Color selected according to the user choice.
   *
   * @param color new color selected by the user.
   */
  public setPenColor(Color color)
}