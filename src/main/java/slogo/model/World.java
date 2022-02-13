package slogo.model;

import javafx.scene.shape.Line;

public interface World extends Iterable<Actor> {
    int getActorCount();
    void addActor(Actor actor);
    Actor getActor(int index);
    void removeActor(Actor actor);
    int removeActorAt(int index);
    int getLineCount();
    Line getLine(int index);
}
