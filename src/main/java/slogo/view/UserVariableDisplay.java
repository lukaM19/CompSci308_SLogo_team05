package slogo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;

public class UserVariableDisplay extends InfoDisplay {

  List<String> values = new ArrayList<>();

  public UserVariableDisplay(int width, int height, String identifier, ResourceBundle resources,
      ResourceBundle errorResources) {
    super(width, height, identifier, resources, errorResources);
  }

  @Override
  protected void handleChange(ObservableList<String> items, int i) {

  }


}
