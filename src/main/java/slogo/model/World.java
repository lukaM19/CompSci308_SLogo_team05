package slogo.model;

import javafx.scene.shape.Line;

import java.util.Iterator;
import java.util.List;

/**
 * The World class represents the state of the world and the actors and lines in it.
 * The Actors in the world can be iterated through using an iterator or for-each loop.
 * Getting Actors and Lines works similarly to how Lists operate.
 */
public class World implements Iterable<Actor> {
    private List<Actor> actors;
    private List<Line> lines;

    /**
     *
     * @return The number of actors in the world
     */
    public int getActorCount() {
        return actors.size();
    }

    /**
     * Adds an actor to the world.
     * @param actor The actor to add
     */
    public void addActor(Actor actor) {
        actors.add(actor);
    }

    /**
     * Gets an actor at the specified index.
     * @param index The index of the actor to get
     * @return The actor at that index
     */
    public Actor getActor(int index) {
        return actors.get(index);
    }

    /**
     * Removes an actor from the world.
     * Does nothing if the actor is not in the world.
     * @param actor The actor to remove
     */
    public void removeActor(Actor actor) {
        actors.remove(actor);
    }

    /**
     * Removes the actor at the specified index from the world.
     * @param index The index from which to remove the actor
     * @return The actor that was removed
     */
    public Actor removeActorAt(int index) {
        return actors.remove(index);
    }

    /**
     * Finds the actor with the specified ID
     * @param ID The ID of the actor to find
     * @return The actor with that ID
     */
    public Actor getActorByID(String ID) {
        return actors.stream().filter(a -> a.getID().equals(ID)).findAny().orElseThrow();
    }

    /**
     *
     * @return The number of lines in the world
     */
    public int getLineCount() {
        return lines.size();
    }

    /**
     * Gets the line at a specified index.
     * @param index The index of the line to get
     * @return The line at that index
     */
    public Line getLine(int index) {
        return lines.get(index);
    }

    @Override
    public Iterator<Actor> iterator() {
        return actors.iterator();
    }
}
