// a parsed command telling the turtle to put its pen down is executed
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

class ActorParameterChange extends ActorCommand {
    private String actorParam;
    private String newState;

    ...

    public Object execute(World world) {
        Actor target = world.getActorByID(ID);
        ...
        switch(actorParam) {
            ...
            case "penDown":
                if(target instanceof Turtle) {
                    target.setPenDown(Boolean.parseBoolean(newState));
                    return target.isPenDown();
                } else {
                    throw new IllegalArgumentException("Some message about the actor not being a turtle");
                }
                break;
            ...
        }
        ...
        return null;
    }
}