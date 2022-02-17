package slogo.view;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

public class ToolBar extends HBox {
    private final String TOOLBAR_RESOURCES_PATH="/slogo/view/";

    private ResourceBundle myResources;
    private String[] elements;
    public ToolBar(){
    setResources(TOOLBAR_RESOURCES_PATH+"ToolBarElements");
    elements= myResources.getString("toolBarElements").split(",");
    setUpToolBar();
    }

    private void setUpToolBar(){
        for(String element : elements){
            MenuButton result = new MenuButton(myResources.getString(element),null);
            setUpButton(element,result);
            this.getChildren().addAll(result);

        }
    }

    private void setUpButton(String element,MenuButton currentButton) {
       String[] buttonItems= myResources.getString(element+"List").split(",");
       for(String item : buttonItems){
           currentButton.getItems().add(makeMenuItem(item));
       }

    }
    private MenuItem makeMenuItem(String itemName) {
        MenuItem item = new MenuItem();
        item.setText(itemName);
        //item.setText(myResources.getString(itemName));
        //item.setOnAction(handler);

        return item;
    }

    public void setResources (String filename) {
        try {
            myResources = ResourceBundle.getBundle(filename);
        }
        catch (NullPointerException | MissingResourceException e) {
            throw new IllegalArgumentException(String.format("Invalid resource file: %s", filename));
        }
    }
}
