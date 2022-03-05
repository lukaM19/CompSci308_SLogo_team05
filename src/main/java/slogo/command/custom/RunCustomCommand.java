package slogo.command.custom;

import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.model.World;

import java.util.List;
import java.util.Map;

public class RunCustomCommand extends Command {
    private static final int ARGUMENTS_PARAMETER = 0;
    private static final int BODY_PARAMETER = 0;

    /***
     * A command that runs a custom command defined by the user
     *
     * @param parameters - the parameters for command
     */
    public RunCustomCommand(List<Command> parameters) {
        super(parameters);
    }

    @Override
    protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
        checkForExactParameterLength(2);
        executeParameter(ARGUMENTS_PARAMETER, world, userVars);
    }

    @Override
    protected Double run() throws CommandException {
        return executeParameter(BODY_PARAMETER, world, userVars).returnVal();
    }
}
