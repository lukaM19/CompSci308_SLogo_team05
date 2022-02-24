package slogo.view;

import static java.lang.Thread.sleep;

import java.util.ArrayList;
import java.util.List;
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
    Point2D ep= new Point2D(0,50);
    MoveInfo move =new MoveInfo("0",sp,ep,0.0,true);

    sp= new Point2D(0,50);
    ep= new Point2D(50,50);
    MoveInfo move1 =new MoveInfo("0",sp,ep,90.0,true);
    sp= new Point2D(50,50);
    ep= new Point2D(50,100);
    MoveInfo move2 =new MoveInfo("0",sp,ep,-90.0,true);

    sp= new Point2D(50,100);
    ep= new Point2D(60,150);
    MoveInfo movee =new MoveInfo("0",sp,ep,35.0,true);

    sp= new Point2D(60,150);
    ep= new Point2D(100,150);
    MoveInfo movee1 =new MoveInfo("0",sp,ep,55.0,false);
    sp= new Point2D(100,150);
    ep= new Point2D(100,200);
    MoveInfo movee2 =new MoveInfo("0",sp,ep,-90.0,true);
    ep= new Point2D(100,200);
    MoveInfo move3 =new MoveInfo("0",ep,60.0);
    List<MoveInfo> moves = new ArrayList<>();
    moves.add(move);
    moves.add(move1);
    moves.add(move2);
    moves.add(movee);
    moves.add(movee1);
    moves.add(movee2);
    //moves.add(move3);
    mv.handleMove(moves);
//

    //mv.handleMove(move2);
   //double[] c= {50,50};
   //mv.handleMove(c,45);
   //double[] d= {200,50};
   //mv.handleMove(d,180);
   //double[] e= {200,100};
   //mv.handleMove(e,90);
  }
}

