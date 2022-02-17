package slogo.command.test.bad;

import slogo.Command;
import slogo.parser.SlogoCommand;

import java.util.List;

@SlogoCommand(keyword="badtest", arguments=0)
class TestCommandBadConstructor implements Command {
    public TestCommandBadConstructor(int arg1) {

    }

    @Override
    public void setArguments(List<Command> args) {

    }

    @Override
    public Object execute() {
        return 1;
    }
}
