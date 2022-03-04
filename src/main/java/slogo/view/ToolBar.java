package slogo.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

/**
 * Completely dynamically built toolbar menu , which uses reflection. Depends on JavaFX,
 * <class>MainView.java</class>, as well as the controller.
 *
 * @author Luka Mdivani
 */
public class ToolBar extends HBox {

  private static final String TOOLBAR_RESOURCES_PATH = "/slogo/view/";

  private ResourceBundle myToolBarResources;
  private ResourceBundle mySystemResources;
  private ResourceBundle myErrorResources;
  private String[] elements;
  private TurtleScreen myTurtleScreen;
  private Consumer<String> myCSSHandler;
  private Runnable mySaveHandler;
  private Runnable myLoadHandler;
  private Runnable myNewWindowHandler;
  private HelpWindow myHelpWindow ;

  /**
   * The constructor for the toolbar class.
   *
   * @param systemResources the resource bundle which specifies which toolBarElements to use.
   * @param errorResources  the resource bundle for the possible error messages.
   * @param turtleScreen    the turtle screen object, which contains the canvas and the turtles.
   * @param cssHandler      consumer which takes in the user input of selected css styles.
   */
  public ToolBar(ResourceBundle systemResources, ResourceBundle errorResources,
      TurtleScreen turtleScreen,
      Consumer<String> cssHandler, Runnable saveHandler,Runnable loadHandler,Runnable newWindowHandler) {
    mySystemResources = systemResources;
    myErrorResources = errorResources;
    myTurtleScreen = turtleScreen;
    setResources(mySystemResources.getString("ToolBarElements"));
    elements = myToolBarResources.getString("toolBarElements").split(",");
    myCSSHandler = cssHandler;
    myLoadHandler = loadHandler;
    mySaveHandler = saveHandler;
    myNewWindowHandler = newWindowHandler;
    setUpToolBar();
    makeHelpButton();
  }
  private void showHelpWindow(){

    myHelpWindow=new HelpWindow();
    myHelpWindow.displayHelp();
  }

  private void makeHelpButton(){
    try {
      this.getChildren().add(ControlUtil.makeButton(myToolBarResources.getString("helpPrompt"),
          e -> showHelpWindow()));
    }
    catch (MissingResourceException e) {
      ErrorWindow errorWindow= new ErrorWindow(myErrorResources.getString(("toolBarBundleError")));
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
    String[] buttonItems = myToolBarResources.getString(element + "List").split(",");
    int index=0;
    for (String item : buttonItems) {
      currentButton.getItems().add(makeMenuItem(element, item,index));
      index+=1;
    }

  }

  private boolean isColorOption(String element){
    try{
      String type=myToolBarResources.getString(element+"Type");
      if(type.equals("Color")){
        return true;
      }
      else {
        return  false;
      }
    }
    catch (MissingResourceException e){
      return false;
    }
  }

  private MenuItem makeMenuItem(String element, String itemName, int index) {
    MenuItem item = new MenuItem();
    try {
      if(isColorOption(element)) {
        item.setText(index +"-"+ myToolBarResources.getString(element + itemName));
      }
      else{
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
      ErrorWindow errorWindow= new ErrorWindow (
          String.format(myErrorResources.getString("toolBarBundleError"), filename));
    }
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
}
