package slogo.view;

import javafx.application.Application;
import javafx.stage.Stage;

public class ViewPlayground extends Application {

  @Override
  public void start(Stage stage)  {
    MainView mv = new MainView(stage);
    mv.setUpView();
  }
}

