package slogo.view;

import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import slogo.model.MoveInfo;

/**
 * The main class for the GUI, here all the GUI elements are created, it also houses the external
 * API calls. Depends on JavaFX as well as the Controller package to be initialized.
 *
 * @author Luka Mdivani
 */
public class MainView {

  public final int SCENE_WIDTH = 1200;
  public final int SCENE_HEIGHT = 700;
  public final int TURTLE_SCREEN_WIDTH = 700;
  public final int TURTLE_SCREEN_HEIGHT = 500;
  public final String TITLE = "SLogo";
  private final String DEFAULT_RESOURCE_PATH = "/slogo/view/";
  private final String DEFAULT_LANGUAGE = "English";

  private TurtleScreen myTurtleScreen;
  private Stage myStage;
  private Consumer<String> myRunHandler;
  private Consumer<String> myCSSHandler;
  private Scene myScene;
  private ResourceBundle myResources;
  private ResourceBundle myErrorResources;
  private ToolBar myToolBar;

  public MainView(Stage stage, EventHandler<ActionEvent> saveHandler,
      EventHandler<ActionEvent> loadHandler, EventHandler<ActionEvent> newController,
      Consumer<String> runHandler) {

    LanguageSplash ls = new LanguageSplash();
    String languageChoice = ls.returnChoice();
    changeLanguage(languageChoice);
    myStage = stage;
    myRunHandler = runHandler;
    myCSSHandler = e -> setStyleMode(e);

  }

  public void setUpView() {
    BorderPane root = new BorderPane();
    myTurtleScreen = new TurtleScreen(TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT,myResources,myErrorResources);
    InfoDisplay commandHistoryBox = new InfoDisplay(700, 200, "history", myResources);
    InfoDisplay userCommandBox = new InfoDisplay(500, 250, "command", myResources);
    InfoDisplay userVariableBox = new InfoDisplay(500, 250, "variable", myResources);
    CommandInputBox inputBox = new CommandInputBox(commandHistoryBox, myRunHandler,myResources);
    root.setLeft(myTurtleScreen);
    root.setRight(new VBox(userCommandBox, userVariableBox));
    root.setBottom(new HBox(commandHistoryBox, inputBox));

    myToolBar = new ToolBar(myResources,myErrorResources, myTurtleScreen, myCSSHandler);
    root.setTop(myToolBar);

    myScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    setStyleMode(myResources.getString("cssDefault"));
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
      showError(e.getMessage());
    }

  }

  private void changeLanguage(String filepath) {
    try {
      myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PATH + filepath);
      myErrorResources = ResourceBundle.getBundle(
          DEFAULT_RESOURCE_PATH + myResources.getString("errorFilePath"));
    } catch (MissingResourceException e) {
      showError("bundleError" + DEFAULT_LANGUAGE);
      changeLanguage(DEFAULT_LANGUAGE);
    }


  }

}
