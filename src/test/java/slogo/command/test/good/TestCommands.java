package slogo.command.test.good;

import slogo.Command;
import slogo.parser.SlogoCommand;

import java.util.List;

@SlogoCommand(keywords={"testnoargs"})
class TestCommandNoArgs implements Command {

    @Override
    public void setArguments(List<Command> args) {

    }

    @Override
    public Object execute() {
        return 1;
    }
}

@SlogoCommand(keywords={"testonearg"}, arguments=1)
class TestCommandOneArg implements Command {
    private Command argument;

    @Override
    public void setArguments(List<Command> args) {
        argument = args.get(0);
    }

    @Override
    public Object execute() {
        return argument.execute();
    }
}
