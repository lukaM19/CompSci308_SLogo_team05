package slogo.command.custom;

import slogo.command.exception.CommandException;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.model.World;
import slogo.parser.CustomCommandParser;

import java.util.Map;

public class CreateCustomCommand extends Command {
    private CustomCommandParser parser;
    private String keyword;
    private CommandList arguments;
    private CommandList body;

    /***
     * Command that registers a custom command when called
     *
     * @param parser the CustomCommandParser to register this command to
     * @param keyword the keyword of the command to register
     * @param args the arguments to set before calling the command
     * @param body the commands to run when this command is called
     */
    public CreateCustomCommand(CustomCommandParser parser, String keyword, CommandList args, CommandList body) {
        super(null);
        this.parser = parser;
        this.keyword = keyword;
        this.arguments = args;
        this.body = body;
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
