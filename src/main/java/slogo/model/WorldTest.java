package slogo.model;

import java.util.Iterator;
import javafx.scene.shape.Line;

public class WorldTest implements World{

  @Override
  public int getActorCount() {
    return 0;
  }

  @Override
  public void addActor(Actor actor) {

  }

  @Override
  public Actor getActor(int index) {
    return null;
  }

  @Override
  public void removeActor(Actor actor) {

  }

  @Override
  public Actor removeActorAt(int index) {
    return null;
  }

  @Override
  public Actor getActorByID(String ID) {
    return null;
  }

  @Override
  public int getLineCount() {
    return 0;
  }

  @Override
  public Line getLine(int index) {
    return null;
  }

  @Override
  public Iterator<Actor> iterator() {
    return null;
  }
}
