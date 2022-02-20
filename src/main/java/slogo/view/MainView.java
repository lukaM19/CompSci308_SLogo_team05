package slogo.view;

import java.util.function.BiConsumer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView {

  public final int SCENE_WIDTH = 1200;
  public final int SCENE_HEIGHT = 700;
  public final int TURTLE_SCREEN_WIDTH = 700;
  public final int TURTLE_SCREEN_HEIGHT = 500;
  public final String TITLE = "SLogo";

  private TurtleScreen myTurtleScreen;
  private Stage myStage;

  public MainView(Stage stage, EventHandler<ActionEvent> saveHandler,
      EventHandler<ActionEvent> loadHandler,EventHandler<ActionEvent> newController,
      BiConsumer<ActionEvent,String> runHandler) {
    myStage = stage;


  }

  public void setUpView() {
    BorderPane root = new BorderPane();
    myTurtleScreen = new TurtleScreen(TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT);
    InfoDisplay commandHistoryBox = new InfoDisplay(700, 200,"history");
    InfoDisplay userCommandBox = new InfoDisplay(500, 250,"command");
    InfoDisplay userVariableBox = new InfoDisplay(500, 250,"variable");
    CommandInputBox inputBox = new CommandInputBox(commandHistoryBox);
    root.setLeft(myTurtleScreen);
    root.setRight(new VBox(userCommandBox, userVariableBox));
    root.setBottom(new HBox(commandHistoryBox, inputBox));

    ToolBar myToolBar = new ToolBar(myTurtleScreen);
    root.setTop(myToolBar);

    Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    myStage.setScene(scene);
    myStage.setTitle(TITLE);
    myStage.show();
  }

  public void handleMove(double[] end, double degree) {
    myTurtleScreen.moveTurtle(end, degree);
  }

  public void showError(String className, String errorMessage) {
    ErrorWindow err = new ErrorWindow(className + errorMessage);

  }

}
