package slogo.view;

import java.util.List;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import slogo.model.MoveInfo;

public class MainView {

  public final int SCENE_WIDTH = 1200;
  public final int SCENE_HEIGHT = 700;
  public final int TURTLE_SCREEN_WIDTH = 700;
  public final int TURTLE_SCREEN_HEIGHT = 500;
  public final String TITLE = "SLogo";
  private final String DEFAULT_RESOURCE_PATH = "/slogo/view/";

  private TurtleScreen myTurtleScreen;
  private Stage myStage;
  private Consumer<String> myRunHandler;
  private Consumer<String> myCSSHandler;
  private Scene myScene;

  public MainView(Stage stage, EventHandler<ActionEvent> saveHandler,
      EventHandler<ActionEvent> loadHandler,EventHandler<ActionEvent> newController,
      Consumer<String> runHandler) {
    myStage = stage;
    myRunHandler=runHandler;
    myCSSHandler=e->setStyleMode(e);

  }

  public void setUpView() {
    BorderPane root = new BorderPane();
    myTurtleScreen = new TurtleScreen(TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT);
    InfoDisplay commandHistoryBox = new InfoDisplay(700, 200,"history");
    InfoDisplay userCommandBox = new InfoDisplay(500, 250,"command");
    InfoDisplay userVariableBox = new InfoDisplay(500, 250,"variable");
    CommandInputBox inputBox = new CommandInputBox(commandHistoryBox,myRunHandler);
    root.setLeft(myTurtleScreen);
    root.setRight(new VBox(userCommandBox, userVariableBox));
    root.setBottom(new HBox(commandHistoryBox, inputBox));

    ToolBar myToolBar = new ToolBar(myTurtleScreen,myCSSHandler);
    root.setTop(myToolBar);

    myScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    myStage.setScene(myScene);
    myStage.setTitle(TITLE);
    myStage.show();
  }

  public void handleMove(List<MoveInfo> moveInfo) {
    myTurtleScreen.moveTurtle(moveInfo);
  }

  public void showError(String className, String... errorMessage) {
    ErrorWindow err = new ErrorWindow(className + errorMessage);

  }
  private void setStyleMode(String styleMode) {
    myScene.getStylesheets().clear();
    try {
      myScene.getStylesheets().add(
          getClass().getResource(DEFAULT_RESOURCE_PATH + styleMode + ".css").toExternalForm());
    } catch (NullPointerException e) {
      showError(e.getMessage() + "test");
   }

  }

}
