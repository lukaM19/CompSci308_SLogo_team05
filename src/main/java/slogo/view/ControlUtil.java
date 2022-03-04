package slogo.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * Utility class for making control elements
 *
 * @author Luka Mdivani
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


    return result;

  }

  /**
   * Make a button with a given name and associated event, also set id.
   *
   * @param name name of button
   * @param e the event handler
   * @param id the tag id of Node.
   * @return return the new button
   */
  public static Button makeButton(String name, EventHandler<ActionEvent> e,String id){
    Button result = new Button(name);
    result.setOnAction(e);
    result.setId(id);

    return result;

  }



}
