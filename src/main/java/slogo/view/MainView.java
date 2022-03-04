package slogo.view;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
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
  private String selectedLanguage;

  private TurtleScreen myTurtleScreen;
  private InfoDisplay userCommandBox;
  private InfoDisplay commandHistoryBox;
  private InfoDisplay userVariableBox;
  private final Stage myStage;
  private Consumer<String> myRunHandler;
  private Consumer<String> myCSSHandler;
  private Runnable mySaveHandler;
  private Runnable myLoadHandler;
  private Runnable myNewWindowHandler;
  private Scene myScene;
  private ResourceBundle myResources;
  private ResourceBundle myErrorResources;
  private LanguageSplash ls;

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
      Runnable loadHandler, Runnable newController,
      Consumer<String> runHandler) {

    myStage = stage;
    mySaveHandler = saveHandler;
    myRunHandler = runHandler;
    myCSSHandler = e -> setStyleMode(e);
    myLoadHandler = loadHandler;
    myNewWindowHandler = newController;

  }


  /**
   * Method which lets user select a language, and launches a UI in that language.
   */
  public void setUpView() {
    Runnable UISetUp = () -> setUpGUI();
    Consumer<String> languageSetter = s -> setSelectedLanguage(s);
    ls = new LanguageSplash(myStage, UISetUp, languageSetter);

  }

  private void setSelectedLanguage(String s) {
    selectedLanguage = s;
    changeLanguage(selectedLanguage);
  }

  /**
   * builds the UI and puts it into the main root, and shows the stage.
   */
  private void setUpGUI() {
    BorderPane root = new BorderPane();
    myTurtleScreen = new TurtleScreen(TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT, myResources,
        myErrorResources);
    commandHistoryBox = new InfoDisplay(TURTLE_SCREEN_WIDTH, HISTORY_SCREEN_HEIGHT,
        "history", myResources);
    userCommandBox = new InfoDisplay(INFO_SCREEN_WIDTH, INFO_SCREEN_HEIGHT, "command",
        myResources);
    userVariableBox = new InfoDisplay(INFO_SCREEN_WIDTH, INFO_SCREEN_HEIGHT, "variable",
        myResources);
    CommandInputBox inputBox = new CommandInputBox(commandHistoryBox.getEntryConsumer(),
        myRunHandler, myResources);
    root.setLeft(myTurtleScreen);
    root.setRight(new VBox(userCommandBox, userVariableBox));
    root.setBottom(new HBox(commandHistoryBox, inputBox));

    ToolBar myToolBar = new ToolBar(myResources, myErrorResources, myTurtleScreen, myCSSHandler,
        mySaveHandler, myLoadHandler, myNewWindowHandler);
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

  /**
   * return the language selection.
   *
   * @return selected Language by the user
   */
  public String getLanguage() {
    return selectedLanguage;
  }

  /**
   * returns consumer maps for pen,canvas color and turtle design.
   *
   * @return the consumers for setting style values from command
   */
  public Map getConsumerMap() {
    return myTurtleScreen.getStyleListeners();
  }

  /**
   * getter of a consumer so it can be passed to parser through consumer
   *
   * @return consumer which adds entries to user variable list
   */
  public Consumer<String> getUserVariableConsumer() {
    return userVariableBox.getEntryConsumer();
  }

  /**
   * getter of a consumer so it can be passed to parser through consumer
   *
   * @return consumer which adds entries to user command list
   */
  public Consumer<String> getUserCommandConsumer() {
    return userCommandBox.getEntryConsumer();
  }

}
