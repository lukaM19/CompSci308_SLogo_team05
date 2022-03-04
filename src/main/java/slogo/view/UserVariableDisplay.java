package slogo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class UserVariableDisplay extends InfoDisplay{
  List<String> values = new ArrayList<>();
  public UserVariableDisplay(int width, int height, String identifier, ResourceBundle resources){
    super(width, height, identifier, resources);
  }

  @Override
  protected void handleChange() {

  }


}
