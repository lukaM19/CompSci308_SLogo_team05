package slogo.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

public class ToolBar extends HBox {

  private final String TOOLBAR_RESOURCES_PATH = "/slogo/view/";

  private ResourceBundle myToolBarResources;
  private String[] elements;
  private TurtleScreen myTurtleScreen;
  private Consumer<String> myCSSHandler;


  public ToolBar(TurtleScreen turtleScreen,Consumer<String> cssHandler) {
    myTurtleScreen = turtleScreen;
    setResources(TOOLBAR_RESOURCES_PATH + "ToolBarElements");
    elements = myToolBarResources.getString("toolBarElements").split(",");
    myCSSHandler=cssHandler;
    setUpToolBar();
  }

  private void setUpToolBar() {
    for (String element : elements) {
      String elementName=myToolBarResources.getString(element);
      MenuButton result = new MenuButton(elementName, null);
      setUpButton(element, result);
      result.setId(element);
      this.getChildren().addAll(result);

    }
  }

  private void setUpButton(String element, MenuButton currentButton) {
    String[] buttonItems = myToolBarResources.getString(element + "List").split(",");
    for (String item : buttonItems) {
      currentButton.getItems().add(makeMenuItem(element,item));

    }

  }

  private MenuItem makeMenuItem(String element,String itemName) {
    MenuItem item = new MenuItem();
    item.setText(itemName);
    item.setOnAction(e -> {
      try {
        getMethod(element+"Method").invoke(this, itemName);

      } catch (IllegalAccessException ex) {
        ex.printStackTrace();
      } catch (InvocationTargetException ex) {
        ex.printStackTrace();
      }
    });
    item.setId(itemName);
    return item;
  }

  private Method getMethod(String methodID) {
    try {
      String methodName=myToolBarResources.getString(methodID);
      Class<?> c = this.getClass();
      return c.getDeclaredMethod(methodName, String.class);
    } catch (Exception e) {
      ErrorWindow err =new ErrorWindow("Check toolBar.properties Method Name Specification");
      return null;
    }
  }

  public void setResources(String filename) {
    try {
      myToolBarResources = ResourceBundle.getBundle(filename);
    } catch (NullPointerException | MissingResourceException e) {
      throw new IllegalArgumentException(String.format("Invalid resource file: %s", filename));
    }
  }

  private void setTurtleScreenColor(String color){
    myTurtleScreen.setColor(color);
  }

  private void setTurtleInkColor(String color){
    myTurtleScreen.setInkColor(color);
  }

  private void setTurtleDesign(String filepath){
    myTurtleScreen.setImage(filepath);
  }

  private void setLanguage(String language){}

  private void setUIStyle(String filepath){myCSSHandler.accept(filepath);}
}
