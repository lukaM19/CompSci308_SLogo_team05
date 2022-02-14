//User wants to change the color of the canvas
class MainView{
    private TurtleScreen myTurtleScreen;
  ...
    //TurtleScreen listener is activated
  try{
    myTurtleScreen.setColor(newColor);
    }
  catch{
    myTurtleScreen.setColor(DEFAULT_COLOR);
    displayException("Invalid color, using default color instead \n "+E.getMessage());
  }
}
