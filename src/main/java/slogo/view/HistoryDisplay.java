package slogo.view;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.collections.ObservableList;

public class HistoryDisplay extends InfoDisplay {

  private Consumer<String> myRunConsumer;
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
    super(width, height, identifier, resources, errorResources);
    try {
      getButtonBox().getChildren()
          .add(
              ControlUtil.makeButton(getResources().getString("reRunPrompt"), e -> reRunCommand()));
    } catch (MissingResourceException e) {
      ErrorWindow err = new ErrorWindow(getErrorResources().getString("bundleError"));
    }
    myRunConsumer = runConsumer;
    pasteConsumerInitializer = (consumer) -> initializePasteConsumer(consumer);
  }

  private void reRunCommand() {
    if (!getListView().getSelectionModel().isEmpty()) {
      String entry = getListView().getSelectionModel().getSelectedItem();
      myRunConsumer.accept(entry);
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

  public Consumer<Consumer<String>> getPasteInitializer() {
    return pasteConsumerInitializer;
  }


}
