package slogo.view;

import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.collections.ObservableList;

/**
 * InfoDisplay extension class which would implement specifics of user commands display window.
 */
public class UserCommandsDisplay extends InfoDisplay {

  /**
   * Main constructor for the InfoDisplay.
   *
   * @param width          width of display window.
   * @param height         of display window.
   * @param identifier     identifier to assign ID to javaFX node.
   * @param resources      the resource bundle for displayed text.
   * @param errorResources
   * @param runHandler
   */
  public UserCommandsDisplay(int width, int height, String identifier,
      ResourceBundle resources,
      ResourceBundle errorResources,
      Consumer<String> runHandler) {
    super(width, height, identifier, resources, errorResources, runHandler);
  }

  @Override
  protected void handleSingleConsumerInput(String entry) {
    addToList(entry);
  }

  @Override
  protected void handleChange(ObservableList<String> items, int i) {
    //FIXME
  }
}
