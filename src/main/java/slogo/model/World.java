package slogo.model;

import java.util.ArrayList;
import java.util.Collection;
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
    private List<Actor> activeActors;
    private List<Line> lines;
    private Collection<String> commandHistory;

    public World() {
        actors = new ArrayList<>();
        lines = new ArrayList<>();
        commandHistory = new ArrayList<>();
        activeActors = new ArrayList<>();
    }
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
    public Actor getActorByIndex(int index) {
        return actors.get(index);
    }

    /**
     * Removes an actor from the world.
     * Does nothing if the actor is not in the world.
     * @param actor The actor to remove
     */
    public void removeActor(Actor actor) {
        actors.remove(actor);
        activeActors.remove(actor);
    }

    /**
     * Removes the actor at the specified index from the world.
     * @param index The index from which to remove the actor
     * @return The actor that was removed
     */
    public Actor removeActorAt(int index) {
        Actor remove = actors.remove(index);
        activeActors.remove(remove);
        return remove;
    }

    /**
     * Finds the actor with the specified ID
     * @param ID The ID of the actor to find
     * @return The actor with that ID
     */
    public Actor getActorByID(double ID) {
        return actors.stream().filter(a -> a.getID() == (ID)).findAny().orElseThrow();
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

    /**
     * Adds new command history to command history
     *
     * @param newCommandHistory to add to commandHistory
     */
    void saveCommandHistory(String newCommandHistory) {
        commandHistory.add(newCommandHistory);
    }

    /***
     * @return command history
     */
    public Collection<String> getCommandHistory() {
        return commandHistory;
    }

    /***
     * Sets active actors list
     *
     * @param ids list of ids
     */
    public void setActiveActors(List<Integer> ids) {
        activeActors.clear();
        for(int id: ids) {
            if(hasActor(id)) {
                activeActors.add(getActorByID(id));
            } else {
                Actor newActor = new Actor(id);
                actors.add(newActor);
                activeActors.add(newActor);
            }
        }
    }

    /***
     * @return true if an actor with the given id exists
     */
    public boolean hasActor(int id) {
        return actors.stream().anyMatch(a -> a.getID() == (id));
    }

    @Override
    public Iterator<Actor> iterator() {
        return actors.iterator();
    }
}
