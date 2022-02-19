package slogo.view;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

public class ToolBar extends HBox {

  private final String TOOLBAR_RESOURCES_PATH = "/slogo/view/";

  private ResourceBundle myResources;
  private String[] elements;
  private TurtleScreen myTurtleScreen;
  private Class<?> currClass;

  public ToolBar(TurtleScreen turtleScreen) {
    myTurtleScreen = turtleScreen;
    setResources(TOOLBAR_RESOURCES_PATH + "ToolBarElements");
    elements = myResources.getString("toolBarElements").split(",");
    setUpToolBar();
  }

  private void setUpToolBar() {
    for (String element : elements) {
      MenuButton result = new MenuButton(myResources.getString(element), null);
      setUpButton(element, result);
      this.getChildren().addAll(result);

    }
  }

  private void setUpButton(String element, MenuButton currentButton) {
    String[] buttonItems = myResources.getString(element + "List").split(",");
    for (String item : buttonItems) {
      currentButton.getItems().add(makeMenuItem(item));
    }

  }

  private MenuItem makeMenuItem(String itemName) {
    MenuItem item = new MenuItem();
    item.setText(itemName);
    //item.setText(myResources.getString(itemName));
    item.setOnAction(e -> {
      try { // testingshi rac gamoiyena im metodit shemidzlia instance variablis sheqmna
        getMethod().invoke(myTurtleScreen, itemName);

      } catch (IllegalAccessException ex) {
        ex.printStackTrace();
      } catch (InvocationTargetException ex) {
        ex.printStackTrace();
      }
    });

    return item;
  }

  private Method getMethod() {
    try {
      Class<?> c = Class.forName("slogo.view.TurtleScreen");
      currClass = c;

      return c.getDeclaredMethod("setColor", String.class);
    } catch (Exception e) {
      System.out.println("f");
      return null;
    }
  }

  public void setResources(String filename) {
    try {
      myResources = ResourceBundle.getBundle(filename);
    } catch (NullPointerException | MissingResourceException e) {
      throw new IllegalArgumentException(String.format("Invalid resource file: %s", filename));
    }
  }
}
