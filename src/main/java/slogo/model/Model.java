package slogo.model;

import slogo.Command;

import java.util.List;

/**
 * The Model class takes commands and execute them, changing the state of the world appropriately
 */
public interface Model {
    /**
     * Executes the passed command, changing the state of the world
     * @param command The command to execute
     * @return A list of all movements that occurred as a result of the command
     */
    List<MoveInfo> executeCommand(Command command);
}
