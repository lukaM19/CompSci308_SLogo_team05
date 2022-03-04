package slogo.view;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class which dynamically builds the help window using a properties file. Depends on JavaFx.
 *
 * @author Luka Mdivani
 */
public class HelpWindow extends TabPane {

  private static final String RESOURCES_PATH = "/slogo/view/";
  private ResourceBundle myInfoResources;
  private String[] tabs;
  private Stage myStage;

  public HelpWindow() {
    myInfoResources = ResourceBundle.getBundle(RESOURCES_PATH + "InfoElementsEnglish");
    tabs = myInfoResources.getString("tabElements").split(",");
    setUpTabs();
    myStage = new Stage();
    Scene myScene = new Scene(new VBox(this));
    myStage.setScene(myScene);
    this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);


  }

  private void setUpTabs() {
    for (String tab : tabs) {
      Tab result = new Tab(myInfoResources.getString(tab));
      setUpPage(tab, result);
      this.getTabs().add(result);
    }
  }

  private void setUpPage(String tab, Tab result) {
    String[] list = myInfoResources.getString(tab + "List").split(",");
    ObservableList<String> items = FXCollections.observableArrayList();
    ListView<String> myListView = new ListView<>();
    for (String entry : list) {
      items.add(myInfoResources.getString(entry));
    }
    myListView.setItems(items);
    VBox wrapper = new VBox(myListView);
    ScrollPane tabPage = new ScrollPane(wrapper);
    tabPage.setFitToWidth(true);
    result.setContent(tabPage);
  }

  public void displayHelp() {
    myStage.showAndWait();
  }

}
