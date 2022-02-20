package slogo.view;

import static java.lang.Thread.sleep;

import java.util.function.BiConsumer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class ViewPlayground extends Application {

  @Override
  public void start(Stage stage) throws InterruptedException {
    EventHandler<ActionEvent> loadHandler = null;
    EventHandler<ActionEvent> newControllerHandler = null;
    EventHandler<ActionEvent> saveHandler = null;
    BiConsumer<ActionEvent,String > bc = null;
    MainView mv = new MainView(stage,saveHandler,loadHandler,newControllerHandler,bc);
    mv.setUpView();
    double[] b = {10, 50};
    mv.handleMove(b,45);
   double[] c= {50,50};
   mv.handleMove(c,45);
   double[] d= {200,50};
   mv.handleMove(d,180);
   double[] e= {200,100};
   mv.handleMove(e,90);
  }
}

