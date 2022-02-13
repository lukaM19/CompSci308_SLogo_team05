package slogo.model;

import javafx.scene.shape.Line;

/**
 * The World class represents the state of the world and the actors and lines in it.
 * The Actors in the world can be iterated through using an iterator or for-each loop.
 * Getting Actors and Lines works similarly to how Lists operate.
 */
public interface World extends Iterable<Actor> {
    /**
     *
     * @return The number of actors in the world
     */
    int getActorCount();

    /**
     * Adds an actor to the world.
     * @param actor The actor to add
     */
    void addActor(Actor actor);

    /**
     * Gets an actor at the specified index.
     * @param index The index of the actor to get
     * @return The actor at that index
     */
    Actor getActor(int index);

    /**
     * Removes an actor from the world.
     * Does nothing if the actor is not in the world.
     * @param actor The actor to remove
     */
    void removeActor(Actor actor);

    /**
     * Removes the actor at the specified index from the world.
     * @param index The index from which to remove the actor
     * @return The actor that was removed
     */
    Actor removeActorAt(int index);

    /**
     * Finds the actor with the specified ID
     * @param ID The ID of the actor to find
     * @return The actor with that ID
     */
    Actor getActorByID(String ID);

    /**
     *
     * @return The number of lines in the world
     */
    int getLineCount();

    /**
     * Gets the line at a specified index.
     * @param index The index of the line to get
     * @return The line at that index
     */
    Line getLine(int index);
}
