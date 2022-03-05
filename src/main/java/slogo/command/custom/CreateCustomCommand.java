package slogo.command.custom;

import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.model.World;
import slogo.parser.CustomCommandParser;

import java.util.List;
import java.util.Map;

public class CreateCustomCommand extends Command {
    private CustomCommandParser parser;
    private String keyword;
    private CommandList arguments;
    private CommandList body;

    /***
     * Command that registers a custom command when called
     *
     * @param keyword the keyword of the command to register
     */
    public CreateCustomCommand(CustomCommandParser parser, String keyword, CommandList args, CommandList body) {
        super(null);
    }

    @Override
    protected void setUpExecution(World world, Map<String, Double> userVars) throws CommandException {
    }

    @Override
    protected Double run() throws CommandException {
        parser.registerCommand(keyword, arguments, body);
        return 1d;
    }
}
