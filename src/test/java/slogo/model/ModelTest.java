package slogo.model;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.actorcommand.move.relative.ValueDistance;
import slogo.command.actorcommand.move.relative.ValueTurnRelative;
import slogo.command.general.CommandList;
import slogo.command.value.GenericValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {
    private static final double TOLERANCE = 0.00001;
    private Model model;

    @BeforeEach
    void setup() {
        model = new Model();
    }

    // Ensures the model starts with a world containing a single turtle at 0,0 and facing 0 degrees
    @Test
    void testModelInit() {
        assertNotNull(model.getWorld());
        assertEquals(model.getWorld().getActorCount(), 1);
        Actor actor = model.getWorld().getActor(0);
        assertNotNull(actor);
        assertEquals(actor.getClass(), Turtle.class);
        assertEquals(actor.getPosition(), new Point2D(0, 0));
        assertEquals(actor.getHeading(), 0);
    }

    @Test
    void testExecuteCommands() {
        List<MoveInfo> expectedMoveInfos = new ArrayList<>(List.of(
                new MoveInfo("0", new Point2D(0, 0), new Point2D(0, 10), 0, false),
                new MoveInfo("0", new Point2D(0, 10), Math.PI / 2),
                new MoveInfo("0", new Point2D(0, 10), new Point2D(-5, 10), Math.PI / 2, false)
        ));
        List<MoveInfo> moveInfos = new ArrayList<>();
        Map<String, String> impliedParameters = new HashMap<>();
        impliedParameters.put("actorID", "0");
        impliedParameters.put("scale", "1");
        var north10 = new ValueDistance(List.of(new GenericValue(10d)));
        north10.setImpliedParameters(impliedParameters);
        var left90 = new ValueTurnRelative(List.of(new GenericValue(Math.PI / 2)));
        left90.setImpliedParameters(impliedParameters);
        var east5 = new ValueDistance(List.of(new GenericValue(-5d)));
        east5.setImpliedParameters(impliedParameters);

        // North 10
        assertDoesNotThrow(() -> moveInfos.addAll(model.executeCommand(north10)));
        assertP2DEquals(new Point2D(0, 10), model.getWorld().getActor(0).getPosition());
        assertEquals(0, model.getWorld().getActor(0).getHeading());
        // Left 90
        assertDoesNotThrow(() -> moveInfos.addAll(model.executeCommand(left90)));
        assertP2DEquals(new Point2D(0, 10), model.getWorld().getActor(0).getPosition());
        assertEquals(Math.PI / 2, model.getWorld().getActor(0).getHeading());
        // East 5
        assertDoesNotThrow(() -> moveInfos.addAll(model.executeCommand(east5)));
        assertP2DEquals(new Point2D(-5, 10), model.getWorld().getActor(0).getPosition());
        assertEquals(Math.PI / 2, model.getWorld().getActor(0).getHeading());

        assertEquals(expectedMoveInfos, moveInfos);
    }

    @Test
    void testExecuteManyCommands() {
        List<MoveInfo> expectedMoveInfos = new ArrayList<>(List.of(
                new MoveInfo("0", new Point2D(0, 0), new Point2D(0, 10), 0, false),
                new MoveInfo("0", new Point2D(0, 10), Math.PI / 2),
                new MoveInfo("0", new Point2D(0, 10), new Point2D(-5, 10), Math.PI / 2, false)
        ));
        List<MoveInfo> moveInfos = new ArrayList<>();
        Map<String, String> impliedParameters = new HashMap<>();
        impliedParameters.put("actorID", "0");
        impliedParameters.put("scale", "1");
        var north10 = new ValueDistance(List.of(new GenericValue(10d)));
        north10.setImpliedParameters(impliedParameters);
        var left90 = new ValueTurnRelative(List.of(new GenericValue(Math.PI / 2)));
        left90.setImpliedParameters(impliedParameters);
        var east5 = new ValueDistance(List.of(new GenericValue(-5d)));
        east5.setImpliedParameters(impliedParameters);

        assertDoesNotThrow(() -> moveInfos.addAll(model.executeCommand(new CommandList(List.of(north10, left90, east5)))));
        assertP2DEquals(new Point2D(-5, 10), model.getWorld().getActor(0).getPosition());
        assertEquals(Math.PI / 2, model.getWorld().getActor(0).getHeading());

        assertEquals(expectedMoveInfos, moveInfos);
    }

    private void assertP2DEquals(Point2D pt1, Point2D pt2) {
        System.out.println("Testing: " + pt1 + " " + pt2);
        assertTrue(() -> Math.abs(pt1.distance(pt2)) < TOLERANCE);
    }

}
