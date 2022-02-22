package slogo.view;

import static java.lang.Thread.sleep;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.stage.Stage;
import slogo.model.MoveInfo;

public class ViewPlayground extends Application {

  @Override
  public void start(Stage stage) throws InterruptedException {
    EventHandler<ActionEvent> loadHandler = null;
    EventHandler<ActionEvent> newControllerHandler = null;
    EventHandler<ActionEvent> saveHandler = null;
    Consumer<String > bc = null;
    MainView mv = new MainView(stage,saveHandler,loadHandler,newControllerHandler,bc);
    mv.setUpView();

    Point2D sp= new Point2D(0,0);
    Point2D ep= new Point2D(10,50);
    MoveInfo move =new MoveInfo("0",sp,ep,45.0,true);
    mv.handleMove(move);

    //sp= new Point2D(10,50);
    //ep= new Point2D(50,50);
    //MoveInfo move1 =new MoveInfo("0",sp,ep,45.0,false);
    //mv.handleMove(move1);
//
    //ep= new Point2D(50,50);
    //MoveInfo move2 =new MoveInfo("0",ep,90.0);
    //mv.handleMove(move2);
   //double[] c= {50,50};
   //mv.handleMove(c,45);
   //double[] d= {200,50};
   //mv.handleMove(d,180);
   //double[] e= {200,100};
   //mv.handleMove(e,90);
  }
}

