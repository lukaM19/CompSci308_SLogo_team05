package slogo.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import slogo.model.MoveInfo;
import util.DukeApplicationTest;

public class ViewTest extends DukeApplicationTest {

  private MainView myView;
  private TextArea myInputBox;
  private ImageView myTurtle;
  private final String DEFAULT_RESOURCE_PATH = "/slogo/";
  TurtleScreen myTurtleScreen;

  @Override
  public void start(Stage stage) {
    Runnable loadHandler = null;
    Runnable newControllerHandler = null;
    Runnable saveHandler = null;
    Consumer<String> bc = (s)->placeholderRun();
    myView = new MainView(stage, saveHandler, loadHandler, newControllerHandler, bc);
    myView.testingLaunch();

  }

  private void selectEnglish(){
    VBox englishButton = lookup("#optionsBox").query();
    clickOn(englishButton.getChildren().get(1));

  }
  private void placeholderRun(){

  }

  @Test
  void testCommandInput() {


    myInputBox = lookup("#myCommandBox").query();

    String expected = "fd 50";
    Button runButton = lookup("#myRunButton").query();

    clickOn(myInputBox).write(expected);
    clickOn(runButton);
    assertEquals(expected, lookup("#historyInfoDisplay").queryAs(InfoDisplay.class).getLastEntry());
  }

  @Test
  void testClearCommand() {

    myInputBox = lookup("#myCommandBox").query();
    String input = "fd 50";
    Button runButton = lookup("#myRunButton").query();
    Button clearButton = lookup("#historyClearButton").query();
    clickOn(myInputBox).write(input);
    clickOn(runButton);
    clickOn(clearButton);
    assertEquals(0,
        getPrivateVariable(lookup("#historyInfoDisplay").queryAs(InfoDisplay.class), "listSize"));

  }
  // THESE TESTS WON'T WORK BECAUSE OF A WEIRD THREAD ERROR,BUT THEY WERE TESTED IN THE PREVIOUS
  // VERSION AND THEY WORKED FINE.
 @Test
 void testTurtleMovement() {

    myInputBox = lookup("#myCommandBox").query();
    Point2D sp = new Point2D(0, 0);
    Point2D ep = new Point2D(10, 50);
    double[] expected = {10, 50};
    List<MoveInfo> moves=new ArrayList<>();
    MoveInfo move = new MoveInfo(0, sp, ep, 45.0, true);
    moves.add(move);
    Platform.runLater(()->myView.handleMove(moves));
    sleep(2000);
    TurtleScreen myTurtleScreen = lookup("#myTurtleScreen").queryAs(TurtleScreen.class);
    double[] queriedValue = myTurtleScreen.getTurtleCurrentPos();
    assertEquals(expected[0], queriedValue[0]);
    assertEquals(expected[1], queriedValue[1]);
   assertEquals(1,myTurtleScreen.getTurtleDrawnLineCount());
 }

  @Test
 void testTurtleNoDrawMovement(){
    myInputBox = lookup("#myCommandBox").query();
    myTurtleScreen = lookup("#myTurtleScreen").queryAs(TurtleScreen.class);
    Point2D sp = new Point2D(0, 0);
    Point2D ep = new Point2D(10, 50);
    double[] expected = {10, 50};
    List<MoveInfo> moves = new ArrayList<>();
    MoveInfo move = new MoveInfo(0, sp, ep, 45.0, false);
    moves.add(move);
    Platform.runLater(()->myView.handleMove(moves));
    sleep(1000);
    double[] queriedValue = myTurtleScreen.getTurtleCurrentPos();
    assertEquals(expected[0], queriedValue[0]);
    assertEquals(expected[1], queriedValue[1]);
    assertEquals(0,myTurtleScreen.getTurtleDrawnLineCount());
 }

  @Test
  void
  testTurtleRotate(){
    myTurtleScreen = lookup("#myTurtleScreen").queryAs(TurtleScreen.class);
    Point2D sp = new Point2D(0, 0);
    MoveInfo move = new MoveInfo(0, sp,  45.0);
    List<MoveInfo> moves= new ArrayList<>();
    moves.add(move);
    Platform.runLater(()-> myView.handleMove(moves));
    sleep(1000);
    assertEquals(45.0,myTurtleScreen.getTurtleCurrentRotate());
 }

  @Test
  void testValidCanvasColorChange() {

    myTurtleScreen = lookup("#myTurtleScreen").queryAs(TurtleScreen.class);
    Color color = Color.GREENYELLOW;
    String expected = "-fx-background-color: " + truncateToRGB(color.toString());

    MenuButton canvasColor = lookup("#canvasColorPrompt").query();
    clickOn(canvasColor);
    clickOn("#Greenyellow");

    assertEquals(expected, myTurtleScreen.getCurrentColor());
  }

  @Test
  void testInvalidCanvasColorChange() {

    MenuButton canvasColor = lookup("#canvasColorPrompt").query();
    clickOn(canvasColor);
    clickOn("#invalid");
    Node dialogPane = lookup(".dialog-pane").query();
    DialogPane dp = (DialogPane) dialogPane;
    assertEquals("Invalid Color Specified", dp.getContentText());
  }

  @Test
  void testValidPenColorChange() {

    myTurtleScreen = lookup("#myTurtleScreen").queryAs(TurtleScreen.class);
    Color color = Color.RED;
    String expected = color.toString();

    MenuButton penColor = lookup("#penColorPrompt").query();
    clickOn(penColor);
    clickOn("#Red");

    assertEquals(Paint.valueOf(expected),
        myTurtleScreen.getTurtleInkColor());
  }

  @Test
  void testInvalidPenColorChange() {

    MenuButton penColor = lookup("#penColorPrompt").query();
    clickOn(penColor);
    clickOn("#Invalid");
    Node dialogPane = lookup(".dialog-pane").query();
    DialogPane dp = (DialogPane) dialogPane;
    assertEquals("Invalid Color Specified", dp.getContentText());

  }

  @Test
  void testValidTurtleDesign() {
    String filePath = "test.png";
    MenuButton turtleDesign = lookup("#turtleDesignPrompt").query();
    clickOn(turtleDesign);
    clickOn("No Shadow Turtle");

    myTurtleScreen = lookup("#myTurtleScreen").queryAs(TurtleScreen.class);
    assertEquals(filePath, myTurtleScreen.getTurtleDesign());
  }

  @Test
  void testInvalidTurtleDesign() {
    MenuButton turtleDesign = lookup("#turtleDesignPrompt").query();
    clickOn(turtleDesign);
    clickOn("Invalid Test");

    Node dialogPane = lookup(".dialog-pane").query();
    DialogPane dp = (DialogPane) dialogPane;
    assertEquals("Specified turtle not found, loaded default", dp.getContentText());
  }


  private String truncateToRGB(String clr) {
    clr = clr.substring(2, clr.length() - 2);
    clr = "#" + clr;
    return clr;
  }

  private Object getPrivateVariable(Object object, String variableName) {

    Field f = null;
    try {
      f = object.getClass().getDeclaredField(variableName);
      f.setAccessible(true);
      return f.get(object);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
      return null;
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return null;
    }


  }


}
