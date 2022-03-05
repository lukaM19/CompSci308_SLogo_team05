package slogo.command.general;

import slogo.command.exception.CommandException;

import java.util.List;

public class CommandSumList extends CommandList {
    /***
     * Creates a list of commands that are all executed in order.
     * The sum of the commands' values is returned
     *
     * @param parameters - parameters for command
     */
    public CommandSumList(List<Command> parameters) {
        super(parameters);
    }

    /***
     * Runs all the commands in the list
     *
     * @return sum of command results or default value if no commands
     * @throws CommandException if command cannot be executed
     */
    @Override
    protected Double run() throws CommandException {
        if(getParametersSize() == 0) {
            return DEFAULT_VALUE;
        }

        double res = DEFAULT_VALUE;

        for(int i = 0; i < getParametersSize(); i++) {
            res += executeParameter(i).returnVal();
        }
        return res;
    }
}
