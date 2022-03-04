package slogo.view;

import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

/**
 * General class which stores lists of data, used for command history, as well as user defined
 * variables and functions. Depends on JavaFX, as well as parser to get the user defined info to
 * store.
 *
 * @author Luka Mdivani
 */
public class InfoDisplay extends ScrollPane {

  private final ListView<String> list = new ListView<>();
  private ObservableList<String> items = FXCollections.observableArrayList();
  private String lastEntry;
  private ResourceBundle myResources;
  private Consumer<String> addEntryConsumer;
  private int listSize;

  /**
   * Main constructor for the InfoDisplay.
   *
   * @param width      width of display window.
   * @param height     of display window.
   * @param identifier identifier to assign ID to javaFX node.
   * @param resources  the resource bundle for displayed text.
   */
  public InfoDisplay(int width, int height, String identifier, ResourceBundle resources) {
    myResources = resources;
    list.setPrefSize(width, height);
    list.setItems(items);
    this.setId(identifier + "InfoDisplay");
    VBox wrapper = new VBox(createClearButton(identifier), list);
    this.setContent(wrapper);
    list.setEditable(true);
    StringConverter<String> converter = new DefaultStringConverter();
    list.setCellFactory(param -> new TextFieldListCell<>(converter));
    addEntryConsumer = entry -> addToList(entry);

    //list.getSelectionModel().getSelectedItem();
    items.addListener((ListChangeListener<String>) change -> handleChange(change));
  }

  private void handleChange(
      Change<? extends String> change) {
    while (change.next()) {
      if (change.wasReplaced()) {
        for (int i = change.getFrom(); i < change.getTo(); ++i) {
          System.out.println("Updated: " + i + " " + items.get(i));
        }
      }
    }
  }

  /**
   * adds entries to the list.
   *
   * @param newEntry entry to be added
   */
  public void addToList(String newEntry) {
    items.add(newEntry);
    getLastEntry();
    updateItemsSize();
  }

  private void updateItemsSize() {
    listSize = items.size();
  }

  private Button createClearButton(String id) {
    return ControlUtil.makeButton(myResources.getString("clearPrompt"), e -> clearDisplay(),
        id + myResources.getString("clearButtonID"));
  }

  /**
   * package friendly method, used for testing purposes.
   *
   * @return last entry into the list
   */
  String getLastEntry() {

    lastEntry = items.get(items.size() - 1);
    return lastEntry;
  }

  private void clearDisplay() {
    items.clear();
    listSize = items.size();
  }

  public Consumer<String> getEntryConsumer() {
    return addEntryConsumer;
  }
}
