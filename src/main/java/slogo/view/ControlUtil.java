package slogo.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class ControlUtil {

  public static Button makeButton(String name, EventHandler<ActionEvent> e){
    Button result = new Button(name);
    result.setOnAction(e);
    result.setId("Canvas"+name);

    return result;

  }



}
