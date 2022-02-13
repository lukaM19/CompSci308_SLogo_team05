// a parsed command to move the turtle forward is executed
class Model {
    private World world;
    public List<MoveInfo> executeCommand(Command command) {
        Object output = command.execute(world);
        ...
        if(output instanceof List<MoveInfo>) {
            return output;
        } else if(output instanceof MoveInfo) {
            return Arrays.asList(output);
        } else {
            output.toString();
        }
    }
}

class Move extends ActorCommand {
    private double distance;
    private double angle;
    private String ID;

    ...

    public Object execute(World world) {
        Actor target = world.getActorByID(ID);
        MoveInfo result = new MoveInfo();
        result.setStart(target.getPosition());
        result.setHeading(target.getHeading());
        result.setPenDown(target.isPenDown());
        result.setActorID(target.getID());

        target.move(distance, angle);

        result.setEnd(target.getPosition());
        return result;
    }
}