package slogo.model;

import java.util.List;

import slogo.command.exception.CommandException;
import slogo.command.general.Command;

/**
 * The Model class takes commands and execute them, changing the state of the world appropriately
 */
public class Model {
    private World world;

    public Model() {
        world = new World();
        world.addActor(new Turtle("0"));
    }

    /**
     * Executes the passed command, changing the state of the world
     * @param command The command to execute
     * @return A list of all movements that occurred as a result of the command
     */
    List<MoveInfo> executeCommand(Command command) throws CommandException {
        return command.execute(world, null).moveInfos();
    }

    World getWorld() {
        return world;
    }
}
