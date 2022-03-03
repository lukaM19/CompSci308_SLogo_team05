package slogo.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import slogo.command.exception.CommandException;
import slogo.command.general.Command;

/**
 * The Model class takes commands and execute them, changing the state of the world appropriately
 */
public class Model {
    private World world;
    private Environment environment;

    public Model() {
        environment = new Environment();
        world = new World();
        world.addActor(new Turtle("0"));
    }

    /**
     * Executes the passed command, changing the state of the world
     * @param command The command to execute
     * @return A list of all movements that occurred as a result of the command
     */
    public List<MoveInfo> executeCommand(Command command) throws CommandException {
        return command.execute(world, environment).moveInfos();
    }

    World getWorld() {
        return world;
    }

    /***
     * Gets command history of the current world
     *
     * @return list of command history
     */
    public Collection<String> getCommandHistory() {
        return world.getCommandHistory();
    }

    /***
     * Saves commands in world's command history
     *
     * @param commands to save
     */
    public void saveCommands(String commands) {
        world.saveCommandHistory(commands);
    }

}
