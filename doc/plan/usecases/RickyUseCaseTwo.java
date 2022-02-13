// a parsed command representing a group of several commands is executed
class Model {
    private World world;
    public List<MoveInfo> executeCommand(Command command) {
        Object output = command.execute(world);
        ...
        if(output instanceof List<MoveInfo>) {
            return (List<MoveInfo>)output;
        } else if(output instanceof MoveInfo) {
            return Arrays.asList((MoveInfo)output);
        } else {
            output.toString();
        }
    }
}

class CommandGroup extends Command {
    private List<Command> commands;

    ...

    public Object execute(World world) {
        List<MoveInfo> result = new ArrayList<MoveInfo>();
        for(Command command : commands) {
            Object output = command.execute(world);

            if(output instanceof List<MoveInfo>) {
                result.addAll((List<MoveInfo>) output);
            } else if(output instanceof MoveInfo) {
                result.add((MoveInfo) output);
            } else {
                MoveInfo msgInfo = new MoveInfo();
                msgInfo.setMessage(output.toString());
                result.add(msgInfo);
            }
        }
        return result;
    }
}