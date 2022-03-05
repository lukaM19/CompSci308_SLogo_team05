package slogo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.collections.ObservableList;

/**
 * Extended INfoDisplay class which implements the specifics of a user defind variable display
 * window.
 */
public class UserVariableDisplay extends InfoDisplay {

  List<String> values = new ArrayList<>();

  public UserVariableDisplay(int width, int height, String identifier, ResourceBundle resources,
      ResourceBundle errorResources, Consumer<String> runConsumer) {
    super(width, height, identifier, resources, errorResources, runConsumer);
  }

  @Override
  protected void handleSingleConsumerInput(String entry) {
  }


  @Override
  protected void handleChange(ObservableList<String> items, int i) {
    String newEntry = items.get(i);
    double newValue = Double.valueOf(newEntry.split(":")[1]);
    getRunConsumer().accept(" make " + getVarNames().get(i) + " set " + newValue);
  }


}
