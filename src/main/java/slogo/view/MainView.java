package slogo.view;

import java.io.FileNotFoundException;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainView {
  public final int SCENE_WIDTH = 1200;
  public final int SCENE_HEIGHT = 700;
  public final int TURTLE_SCREEN_WIDTH = 700;
  public final int TURTLE_SCREEN_HEIGHT = 500;
  public final String TITLE = "SLogo";

  private Stage myStage;
  public MainView(Stage stage){
    myStage=stage;


  }

  public void setUpView(){
    BorderPane root = new BorderPane();
    TurtleScreen t=  new TurtleScreen(TURTLE_SCREEN_WIDTH,TURTLE_SCREEN_HEIGHT);
    root.setTop(new ToolBar());
    InfoDisplay commandHistoryBox =new InfoDisplay(700,200);
    InfoDisplay userCommandBox =new InfoDisplay(500,250);
    InfoDisplay userVariableBox =new InfoDisplay(500,250);
    CommandInputBox inputBox = new CommandInputBox(commandHistoryBox);
    root.setLeft(t);
    root.setRight(new VBox(userCommandBox,userVariableBox));
    root.setBottom(new HBox(commandHistoryBox,inputBox));
    Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    myStage.setScene(scene);
    myStage.setTitle(TITLE);
    myStage.show();
  }




}
