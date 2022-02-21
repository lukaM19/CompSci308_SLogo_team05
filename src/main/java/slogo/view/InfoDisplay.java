package slogo.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class InfoDisplay extends ScrollPane {
  ListView<String> list = new ListView();
  ObservableList<String> items = FXCollections.observableArrayList ();
  private String lastEntry;
  public InfoDisplay(int width, int height,String identifier){
    list.setPrefSize(width,height);
    list.setItems(items);
    this.setId(identifier+"InfoDisplay");
    VBox wrapper=new VBox(createClearButton(identifier), list);
    this.setContent(wrapper);

  }
  public void addToList(String newEntry){
    items.add("> "+newEntry);getLastEntry();
  }

  private Button createClearButton(String id){
    Button clearButton=new Button("Clear");
    clearButton.setId(id+"ClearButton");
    clearButton.setOnAction(e->clearDisplay());
    return clearButton;
  }
  public String  getLastEntry(){

    lastEntry=items.get(items.size()-1);
    return lastEntry.substring(2, lastEntry.length());
  }

  private void clearDisplay(){
    items.clear();
  }
}
