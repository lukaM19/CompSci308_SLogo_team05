package slogo.view;

import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.collections.ObservableList;

/**
 * Extended INfoDisplay class which implements the specifics of a user defined variable display
 * window.
 */
public class UserVariableDisplay extends InfoDisplay {


  private static final String COMMAND_RESOURCE_PATH = "/slogo/view/";
  private String myLanguage;
  private ResourceBundle commandResources;

  public UserVariableDisplay(int width, int height, String identifier, ResourceBundle resources,
      ResourceBundle errorResources, Consumer<String> runConsumer, String language) {
    super(width, height, identifier, resources, errorResources, runConsumer);
    myLanguage = language;
    commandResources = ResourceBundle.getBundle(COMMAND_RESOURCE_PATH + myLanguage + "Command");
  }

  @Override
  protected void handleSingleConsumerInput(String entry) {
  }


  @Override
  protected void handleChange(ObservableList<String> items, int i) {
    String newEntry = items.get(i);
    double newValue = Double.valueOf(newEntry.split(":")[1]);
    getRunConsumer().accept(
        commandResources.getString("makeCommand") + " " + getVarNames().get(i) + " "
            + commandResources.getString("setCommand") + " " + newValue);
  }


}
