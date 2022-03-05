package slogo.command.custom;

import slogo.command.exception.CommandException;
import slogo.command.general.Command;

import java.util.List;

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
        setParamNumber(2);
    }

    @Override
    protected void setUpExecution() throws CommandException {
        executeParameter(ARGUMENTS_PARAMETER);
    }

    @Override
    protected Double run() throws CommandException {
        return executeParameter(BODY_PARAMETER).returnVal();
    }
}
