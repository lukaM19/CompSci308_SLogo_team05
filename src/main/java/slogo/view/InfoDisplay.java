package slogo.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class InfoDisplay extends ScrollPane {
  ListView<String> list = new ListView();
  ObservableList<String> items = FXCollections.observableArrayList ();
  public InfoDisplay(int width, int height){
    list.setPrefSize(width,height);
    list.setItems(items);
    VBox wrapper=new VBox(list);
    this.setContent(wrapper);

  }
  public void addToList(String newEntry){
    items.add("> "+newEntry);
  }

}
