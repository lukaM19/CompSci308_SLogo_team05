package slogo.model;

import static slogo.command.general.Command.DEFAULT_VALUE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.scene.shape.Line;

import java.util.Iterator;
import java.util.List;

/**
 * The World class represents the state of the world and the actors and lines in it.
 * The Actors in the world can be iterated through using an iterator or for-each loop.
 * Getting Actors and Lines works similarly to how Lists operate.
 */
public class World implements Iterable<Actor> {

    public static final String TURTLE_NUM_KEY = "turtleNumber";
    public static final String ACTIVE_TURTLE_KEY = "activeTurtle";
    public static final String BACKGROUND_KEY = "background";
    public static final String PALETTE_KEY = "palette";
    public static final Set<String> NOT_CHANGEABLE = Set.of(TURTLE_NUM_KEY, ACTIVE_TURTLE_KEY);

    private List<Actor> actors;
    private List<Actor> activeActors;
    private List<Line> lines;
    private Collection<String> commandHistory;
    private Map<String, DoubleLambda> worldVals;

    public World() {
        actors = new ArrayList<>();
        lines = new ArrayList<>();
        commandHistory = new ArrayList<>();
        activeActors = new ArrayList<>();
        worldVals = new HashMap<>();
        worldVals.put(TURTLE_NUM_KEY, () -> actors.size());
        worldVals.put(ACTIVE_TURTLE_KEY, () -> activeActors.get(activeActors.size() - 1).getID());
    }

    /***
     * Returns if the requested value exists
     *
     * @param key to look for in actorVars
     * @return value paired to the given key
     */
    public boolean hasKey(String key) {
        return worldVals.containsKey(key);
    }

    /***
     * Returns the requested value that is paired with the key
     *
     * @param key to look for in actorVars
     * @return value paired to the given key
     */
    public Double getVal(String key) {
        return worldVals.get(key).execute();
    }

    /***
     * Puts the given key-value pair in the actorVars map
     *
     * @param key is the key to put in the map
     * @param val is the value to put in the map
     */
    public boolean putVal(String key, double val) {
        if(!NOT_CHANGEABLE.contains(key)) {
            worldVals.put(key, () -> val);
            return true;
        }
        return false;
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
        if(activeActors.isEmpty()) {
            activeActors.add(actor);
            worldVals.put(ACTIVE_TURTLE_KEY, () -> DEFAULT_VALUE);
        }
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
    public void setActiveActors(List<Double> ids) {
        activeActors.clear();
        for(double id: ids) {
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
     * @return list of active actor ids
     */
    public List<Double> getActiveActorIds() {
        List<Double> ids = new ArrayList<>();
        for(Actor actor: activeActors) {
            ids.add(actor.getID());
        }
        return ids;
    }

    /***
     * Does an action on the active actors
     *
     * @return result of the execution
     */
    public double doActionOnActiveActors(ActorLambda<Actor> lambda) {
        double result = 0d;
        for(Actor actor: activeActors) {
            result = lambda.runMethodOnActor(actor);
        }
        return result;
    }

    /***
     * @return true if an actor with the given id exists
     */
    public boolean hasActor(double id) {
        return actors.stream().anyMatch(a -> a.getID() == (id));
    }

    @Override
    public Iterator<Actor> iterator() {
        return actors.iterator();
    }

    /***
     * @return all actors
     */
    public List<Actor> getAllActors() {
        return actors;
    }
}
