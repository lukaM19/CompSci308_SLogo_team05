package slogo.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
/*
* I think the code is well-designed because it is completely dynamic, it uses reflection to initialize
every component of the toolbar, and is extendable without the need to add any code whatsoever. It
uses a properties file to do this (example EnglishToolBarElements.properties will be attached), to add
new options one would simply need to edit the properties file. I think that this code is well written
simple to understand, follows and uses new class concepts, is named well.

*
I only did 1 refactor, where I didn't change any design because I thought it was already good. I only edited
one if statement because it was unnecessarily complicated.
* https://coursework.cs.duke.edu/compsci308_2022spring/slogo_team05/-/commit/c6791389849a61c4776d7a91b17c244998d12c96

*/

/**
 * Completely dynamically built toolbar menu , which uses reflection. Depends on JavaFX,
 * <class>MainView.java</class>, as well as the controller, also on HelpWindow.
 *
 * @author Luka Mdivani
 */
public class ToolBar extends HBox {

  private static final String TOOLBAR_RESOURCES_PATH = "/slogo/view/";

  private ResourceBundle myToolBarResources;
  private final ResourceBundle myErrorResources;
  private final String[] elements;
  private final TurtleScreen myTurtleScreen;
  private final Consumer<String> myCSSHandler;
  private final Runnable mySaveHandler;
  private final Runnable myLoadHandler;
  private final Runnable myNewWindowHandler;
  private String[] canvasColorOptions;
  private String[] penColorOptions;
  private String[] turtleDesignOptions;

  /**
   * The constructor for the toolbar class. It initializes some necessary instance variables.
   *
   * @param systemResources  the resource bundle which specifies which toolBarElements to use.
   * @param errorResources   the resource bundle for the possible error messages.
   * @param turtleScreen     the turtle screen object, which contains the canvas and the turtles.
   * @param cssHandler       consumer which takes in the user input of selected css styles.
   * @param saveHandler      runnable which activates the method to save a file located in
   *                         controller.
   * @param loadHandler      runnable which activates the method to load a file located in
   *                         controller.
   * @param newWindowHandler runnable which actives new window method in controller.
   */
  public ToolBar(ResourceBundle systemResources, ResourceBundle errorResources,
      TurtleScreen turtleScreen,
      Consumer<String> cssHandler, Runnable saveHandler, Runnable loadHandler,
      Runnable newWindowHandler) {
    myErrorResources = errorResources;
    myTurtleScreen = turtleScreen;
    setResources(systemResources.getString("ToolBarElements"));
    elements = myToolBarResources.getString("toolBarElements").split(",");
    myCSSHandler = cssHandler;
    myLoadHandler = loadHandler;
    mySaveHandler = saveHandler;
    myNewWindowHandler = newWindowHandler;
    setUpToolBar();
    makeHelpButton();
    passOptionsToTurtleScreen();
  }

  private void showHelpWindow() {

    HelpWindow myHelpWindow = new HelpWindow();
    myHelpWindow.displayHelp();
  }

  private void makeHelpButton() {
    try {
      this.getChildren().add(ControlUtil.makeButton(myToolBarResources.getString("helpPrompt"),
          e -> showHelpWindow()));
    } catch (MissingResourceException e) {
      ErrorWindow errorWindow = new ErrorWindow(myErrorResources.getString(("toolBarBundleError")));
    }

  }

  private void setUpToolBar() {
    for (String element : elements) {
      String elementName = myToolBarResources.getString(element);
      MenuButton result = new MenuButton(elementName, null);
      setUpButton(element, result);
      result.setId(element);
      this.getChildren().addAll(result);

    }
  }

  private void setUpButton(String element, MenuButton currentButton) {
    checkIfNeedToKeepList(element);

    String[] buttonItems = myToolBarResources.getString(element + "List").split(",");
    int index = 0;
    for (String item : buttonItems) {
      currentButton.getItems().add(makeMenuItem(element, item, index));
      index += 1;
    }

  }

  private void checkIfNeedToKeepList(String element) {
    if (isColorOption(element)) {
      try {
        getMethod(element + "ListMethod").invoke(this,
            myToolBarResources.getString(element + "List"));
      } catch (IllegalAccessException | InvocationTargetException e) {
        ErrorWindow err = new ErrorWindow(myErrorResources.getString("toolBarBundleError"));
      }
    }
  }

  private boolean isColorOption(String element) {
    try {
      String type = myToolBarResources.getString(element + "Type");
      return type.equals("Color");
    } catch (MissingResourceException e) {
      return false;
    }
  }

  private MenuItem makeMenuItem(String element, String itemName, int index) {
    MenuItem item = new MenuItem();
    try {
      if (isColorOption(element)) {
        //index the options for items which can also be changed from user commands
        item.setText(index + "-" + myToolBarResources.getString(element + itemName));
      } else {
        item.setText(myToolBarResources.getString(element + itemName));
      }
    } catch (MissingResourceException e) {
      ErrorWindow errorWindow = new ErrorWindow(e.getMessage());
    }
    item.setOnAction(e -> {
      try {
        getMethod(element + "Method").invoke(this, itemName);

      } catch (IllegalAccessException | InvocationTargetException ex) {
        ErrorWindow errorWindow = new ErrorWindow(ex.getMessage());
      }
    });
    item.setId(itemName);
    return item;
  }

  private Method getMethod(String methodID) {
    try {
      String methodName = myToolBarResources.getString(methodID);
      Class<?> c = this.getClass();
      return c.getDeclaredMethod(methodName, String.class);
    } catch (Exception e) {
      ErrorWindow err = new ErrorWindow("Check toolBar.properties Method Name Specification");
      return null;
    }
  }

  private void setResources(String filename) {
    try {
      myToolBarResources = ResourceBundle.getBundle(TOOLBAR_RESOURCES_PATH + filename);
    } catch (NullPointerException | MissingResourceException e) {
      ErrorWindow errorWindow = new ErrorWindow(
          String.format(myErrorResources.getString("toolBarBundleError"), filename));
    }
  }

  private void initializeCanvasOptions(String list) {
    canvasColorOptions = list.split(",");
  }

  private void initializePenOptions(String list) {
    penColorOptions = list.split(",");
  }

  private void initializeTurtleOptions(String list) {
    turtleDesignOptions = list.split(",");
  }

  private void setTurtleScreenColor(String color) {
    myTurtleScreen.setColor(color);
  }

  private void setTurtleInkColor(String color) {
    myTurtleScreen.setInkColor(color);
  }

  private void setTurtleDesign(String filepath) {
    myTurtleScreen.setImage(filepath);
  }

  private void setUIStyle(String filepath) {
    myCSSHandler.accept(filepath);
  }

  private void fileCommand(String CommandType) {
    if (CommandType.equals(myToolBarResources.getString("saveCommand"))) {
      mySaveHandler.run();
    }
    if (CommandType.equals(myToolBarResources.getString("loadCommand"))) {
      myLoadHandler.run();
    }
    if (CommandType.equals(myToolBarResources.getString("newWindowCommand"))) {
      myNewWindowHandler.run();
    }
  }

  private void passOptionsToTurtleScreen() {
    myTurtleScreen.setAllStyleOptions(canvasColorOptions, penColorOptions, turtleDesignOptions);
  }
}
