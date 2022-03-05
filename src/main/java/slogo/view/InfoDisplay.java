package slogo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

/**
 * General class which stores lists of data, used for command history, as well as user defined
 * variables and functions. Depends on JavaFX, as well as parser to get the user defined info to
 * store. It has abstract methods for handling some processes caused by change, or when a new entry
 * is accepted
 *
 * @author Luka Mdivani
 */
public abstract class InfoDisplay extends ScrollPane {

  private final ListView<String> listView = new ListView<>();
  private ObservableList<String> items = FXCollections.observableArrayList();
  private String lastEntry;
  private ResourceBundle myResources;
  private ResourceBundle myErrorResources;
  private Consumer<String> addEntryConsumer;
  private int listSize;
  private BiConsumer<String, Double> userEntryConsumer;
  private HBox buttonBox;
  private List<String> userDefinedNames = new ArrayList<>();
  private List<Double> userValues = new ArrayList<>();
  private Consumer<String> myRunConsumer;

  /**
   * Main constructor for the InfoDisplay.
   *
   * @param width      width of display window.
   * @param height     of display window.
   * @param identifier identifier to assign ID to javaFX node.
   * @param resources  the resource bundle for displayed text.
   */
  public InfoDisplay(int width, int height, String identifier, ResourceBundle resources,
      ResourceBundle errorResources, Consumer<String> runHandler) {
    myResources = resources;
    myErrorResources = errorResources;
    myRunConsumer = runHandler;

    listView.setPrefSize(width, height);
    listView.setItems(items);
    this.setId(identifier + "InfoDisplay");

    buttonBox = new HBox(createClearButton(identifier));
    VBox wrapper = new VBox(buttonBox, listView);
    this.setContent(wrapper);

    listView.setEditable(true);
    StringConverter<String> converter = new DefaultStringConverter();
    listView.setCellFactory(param -> new TextFieldListCell<>(converter));

    addEntryConsumer = entry -> handleSingleConsumerInput(entry);
    userEntryConsumer = (name, value) -> {
      userDefinedInfoConsumer(name, value);
    };

    items.addListener((ListChangeListener<String>) change -> detectChange(change));
  }

  protected void userDefinedInfoConsumer(String name, Double value) {
    if (getVarNames().contains(name)) {
      int index = getVarNames().indexOf(name);
      userValues.set(index, value);
      items.set(index, name + ": " + value);
    } else {
      getVarNames().add(name);
      getUserValues().add(value);
      addToList(name + ": " + value);
    }
  }

  protected HBox getButtonBox() {
    return buttonBox;
  }

  protected List<String> getVarNames() {
    return userDefinedNames;
  }

  protected List<Double> getUserValues() {
    return userValues;
  }

  /**
   * Different InfoDisplays need to handle the input differently, History and user command display
   * both use a single string consumer, but they should do different things while adding the string
   * to the list that's why the abstract class exists.
   *
   * @param entry the new entry string
   */
  protected abstract void handleSingleConsumerInput(String entry);

  protected Consumer<String> getRunConsumer() {
    return myRunConsumer;
  }

  protected ResourceBundle getErrorResources() {
    return myErrorResources;
  }

  protected ListView<String> getListView() {
    return listView;
  }

  private void detectChange(
      Change<? extends String> change) {
    while (change.next()) {
      if (change.wasReplaced()) {
        for (int i = change.getFrom(); i < change.getTo(); ++i) {
          handleChange(items, i);
        }
      }
    }
  }

  protected ResourceBundle getResources() {
    return myResources;
  }

  /**
   * Defines what should happen when a user changes the existing entry in an info display. This is
   * an abstract merhod because reaction should be different depending on the user display type.
   *
   * @param items the list of items on the display
   * @param i     index of the changed item
   */
  protected abstract void handleChange(ObservableList<String> items, int i);

  /**
   * adds entries to the list.
   *
   * @param newEntry entry to be added
   */
  protected void addToList(String newEntry) {
    items.add(newEntry);
    getLastEntry();
    updateItemsSize();
  }

  protected void updateItemsSize() {
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
    userValues.clear();
    userDefinedNames.clear();
    listSize = items.size();
  }

  /**
   * Used in parse to add parsed user defined info to display.
   *
   * @return the entry consumer which accepts a string
   */
  public Consumer<String> getEntryConsumer() {
    return addEntryConsumer;
  }

  /**
   * Used in parse to add parsed user defined variable and its respective value to display.
   *
   * @return the entry consumer which accepts a string and value
   */
  public BiConsumer<String, Double> getUserEntryConsumer() {
    return userEntryConsumer;
  }

}
