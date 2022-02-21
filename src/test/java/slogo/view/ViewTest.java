package slogo.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.BiConsumer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ViewTest extends DukeApplicationTest {
  MainView myView;
  TextArea myInputBox;

  @Override
  public void start (Stage stage) {
    EventHandler<ActionEvent> loadHandler = null;
    EventHandler<ActionEvent> newControllerHandler = null;
    EventHandler<ActionEvent> saveHandler = null;
    BiConsumer<ActionEvent,String > bc = null;
    myView = new MainView(stage,saveHandler,loadHandler,newControllerHandler,bc);
    myView.setUpView();
    myInputBox=lookup("#myCommandBox").query();
  }

  @Test
  public void testCommandInput () {
    String expected = "fd 50";
    Button runButton= lookup("#myRunButton").query();
    clickOn(myInputBox).write(expected);
    clickOn(runButton);
    assertEquals(expected,lookup("#historyInfoDisplay").queryAs(InfoDisplay.class).getLastEntry());
  }


}