package slogo.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * Utility class for making control elements
 */
public class ControlUtil {
  private ControlUtil(){
  }

  /**
   * Make a button with a given name and associated event.
   *
   * @param name name of button
   * @param e the event handler
   * @return return the new button
   */
  public static Button makeButton(String name, EventHandler<ActionEvent> e){
    Button result = new Button(name);
    result.setOnAction(e);
    result.setId("Canvas"+name);

    return result;

  }



}
