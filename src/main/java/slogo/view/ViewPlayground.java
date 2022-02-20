package slogo.view;

import static java.lang.Thread.sleep;

import javafx.application.Application;
import javafx.stage.Stage;

public class ViewPlayground extends Application {

  @Override
  public void start(Stage stage) throws InterruptedException {
    MainView mv = new MainView(stage, null, null, null, null);
    mv.setUpView();
    double[] b = {10, 50};
    mv.handleMove(b,45);
   double[] c= {50,50};
   mv.handleMove(c,45);
   double[] d= {200,50};
   mv.handleMove(d,180);

  }
}

