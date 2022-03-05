package slogo.view;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.collections.ObservableList;

/**
 * An extension class of InfoDisplay which implements the display specifics of a command hostory
 * display window.
 *
 * @author Luka Mdivani
 */
public class HistoryDisplay extends InfoDisplay {

  private static final String COMMAND_RESOURCE_PATH = "/slogo/languages";
  private Consumer<String> pasteToInputConsumer;
  private Consumer<Consumer<String>> pasteConsumerInitializer;

  /**
   * Main constructor for the InfoDisplay.
   *
   * @param width      width of display window.
   * @param height     of display window.
   * @param identifier identifier to assign ID to javaFX node.
   * @param resources  the resource bundle for displayed text.
   */
  public HistoryDisplay(int width, int height, String identifier,
      ResourceBundle resources, ResourceBundle errorResources, Consumer<String> runConsumer) {
    super(width, height, identifier, resources, errorResources, runConsumer);
    try {
      getButtonBox().getChildren()
          .add(
              ControlUtil.makeButton(getResources().getString("reRunPrompt"), e -> reRunCommand()));
    } catch (MissingResourceException e) {
      ErrorWindow err = new ErrorWindow(getErrorResources().getString("bundleError"));
    }
    pasteConsumerInitializer = (consumer) -> initializePasteConsumer(consumer);
  }

  @Override
  protected void handleSingleConsumerInput(String entry) {
    addToList(entry);
  }

  private void reRunCommand() {
    if (!getListView().getSelectionModel().isEmpty()) {
      String entry = getListView().getSelectionModel().getSelectedItem();
      getRunConsumer().accept(entry);
      getEntryConsumer().accept(entry);
      getListView().getSelectionModel().clearSelection();
    } else {
      ErrorWindow err = new ErrorWindow(getErrorResources().getString("noSelectionError"));
    }
  }

  @Override
  protected void handleChange(ObservableList<String> items, int i) {
    pasteToInputConsumer.accept(items.get(i));
    items.remove(i);
  }

  private void initializePasteConsumer(Consumer<String> pasteConsumer) {
    pasteToInputConsumer = pasteConsumer;
  }

  /**
   * get the consumer which moves the edited old command to the inputbox, so it can be reRun.
   *
   * @return the consumer which takes the string which should be moved to input
   */
  public Consumer<Consumer<String>> getPasteInitializer() {
    return pasteConsumerInitializer;
  }


}
