package slogo.view;

import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import slogo.model.MoveInfo;

/**
 * The main class for the GUI, here all the GUI elements are created, it also houses the external
 * API calls. Depends on JavaFX as well as the Controller package to be initialized.
 *
 * @author Luka Mdivani
 */
public class MainView {

  public static final int SCENE_WIDTH = 1280;
  public static final int SCENE_HEIGHT = 700;
  public static final int TURTLE_SCREEN_WIDTH = 700;
  public static final int TURTLE_SCREEN_HEIGHT = 500;
  public static final int INFO_SCREEN_WIDTH = 500;
  public static final int INFO_SCREEN_HEIGHT = 250;
  public static final int HISTORY_SCREEN_HEIGHT = 200;

  public static final String TITLE = "SLogo";
  private final String DEFAULT_RESOURCE_PATH = "/slogo/view/";
  private final String DEFAULT_LANGUAGE = "English";
  private final String selectedLanguage;

  private TurtleScreen myTurtleScreen;
  private final Stage myStage;
  private Consumer<String> myRunHandler;
  private Consumer<String> myCSSHandler;
  private Runnable mySaveHandler;
  private Scene myScene;
  private ResourceBundle myResources;
  private ResourceBundle myErrorResources;

  /**
   * sets up the main view, finds out preferred language by user.
   *
   * @param stage         the main stage
   * @param saveHandler   the save handler from controller
   * @param loadHandler   the load handler from controller
   * @param newController the new window create controller
   * @param runHandler    the handler so run commands to model through controller
   */
  public MainView(Stage stage, Runnable saveHandler,
      EventHandler<ActionEvent> loadHandler, EventHandler<ActionEvent> newController,
      Consumer<String> runHandler) {

    LanguageSplash ls = new LanguageSplash();
    selectedLanguage = ls.returnChoice();
    changeLanguage(selectedLanguage);
    myStage = stage;
    mySaveHandler = saveHandler;
    myRunHandler = runHandler;
    myCSSHandler = e -> setStyleMode(e);

  }

  /**
   * builds the UI and puts it into the main root, and shows the stage.
   */
  public void setUpView() {
    BorderPane root = new BorderPane();
    myTurtleScreen = new TurtleScreen(TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT, myResources,
        myErrorResources);
    InfoDisplay commandHistoryBox = new InfoDisplay(TURTLE_SCREEN_WIDTH, HISTORY_SCREEN_HEIGHT,
        "history", myResources);
    InfoDisplay userCommandBox = new InfoDisplay(INFO_SCREEN_WIDTH, INFO_SCREEN_HEIGHT, "command",
        myResources);
    InfoDisplay userVariableBox = new InfoDisplay(INFO_SCREEN_WIDTH, INFO_SCREEN_HEIGHT, "variable",
        myResources);
    CommandInputBox inputBox = new CommandInputBox(commandHistoryBox, myRunHandler, myResources);
    root.setLeft(myTurtleScreen);
    root.setRight(new VBox(userCommandBox, userVariableBox));
    root.setBottom(new HBox(commandHistoryBox, inputBox));

    ToolBar myToolBar = new ToolBar(myResources, myErrorResources, myTurtleScreen, myCSSHandler,
        mySaveHandler);
    root.setTop(myToolBar);

    myScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    setStyleMode(myResources.getString("cssDefault"));
    myStage.setScene(myScene);
    myStage.setTitle(TITLE);
    myStage.show();
  }

  /**
   * api for communication with model, takes in the moves which need to be shown
   *
   * @param moveInfo the lists of moves to be visualized.
   */
  public void handleMove(List<MoveInfo> moveInfo) {
    myTurtleScreen.moveTurtle(moveInfo);
  }

  /**
   * display a visual window of an error.
   *
   * @param className    the name of the clas which caused error
   * @param errorMessage error message
   */
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
      showError(myErrorResources.getString("bundleError") + DEFAULT_LANGUAGE);
      if (!filepath.equals(DEFAULT_LANGUAGE)) {
        changeLanguage(DEFAULT_LANGUAGE);
      }
    }


  }

  /**
   * launches an explorer window for the user to choose the file where they want info to be saved.
   *
   * @return the chosen file by the user
   */
  public File chooseSaveFile() {
    FileChooser fileChooser = new FileChooser();
    return fileChooser.showSaveDialog(myStage);
  }

  /**
   * launches an explorer window for the user to choose the file where they want info to be loaded
   * from.
   *
   * @return the chosen file by the user
   */
  public File chooseLoadFile() {
    FileChooser fileChooser = new FileChooser();
    return fileChooser.showOpenDialog(myStage);
  }

  public String getLanguage() {
    return selectedLanguage;
  }


}
