package slogo.view;

import java.util.ResourceBundle;
import java.util.function.Consumer;

public class HistoryDisplay extends InfoDisplay{
  private Consumer<String> myRunConsumer;
  /**
   * Main constructor for the InfoDisplay.
   *
   * @param width      width of display window.
   * @param height     of display window.
   * @param identifier identifier to assign ID to javaFX node.
   * @param resources  the resource bundle for displayed text.
   */
  public HistoryDisplay(int width, int height, String identifier,
      ResourceBundle resources,Consumer<String> runConsumer) {
    super(width, height, identifier, resources);
    getButtonBox().getChildren().add(ControlUtil.makeButton("ReRun",e->reRunCommand()));
    myRunConsumer=runConsumer;
  }

  private void reRunCommand() {
    if (!getList().getSelectionModel().isEmpty()) {
      String entry = getList().getSelectionModel().getSelectedItem();
      myRunConsumer.accept(entry);
      getEntryConsumer().accept(entry);
      getList().getSelectionModel().clearSelection();
    }
    else {
      ErrorWindow err= new ErrorWindow("Nothing Selected");
    }
  }
  @Override
  protected void handleChange() {
    //getList().
  }


}
