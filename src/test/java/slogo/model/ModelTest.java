package slogo.model;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.command.actor.move.relative.ValueDistance;
import slogo.command.actor.move.relative.ValueTurnRelative;
import slogo.command.general.Command;
import slogo.command.general.CommandList;
import slogo.command.value.GenericValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {
    private static final double TOLERANCE = 0.00001;
    private static final double ACTOR_ID = 0d;
    private static final boolean PEN_DEFAULT = true;
    private Model model;
    private List<MoveInfo> expectedMoveInfos = new ArrayList<>(List.of(
            new MoveInfo(ACTOR_ID, new Point2D(0, 0), new Point2D(0, 10), 0, PEN_DEFAULT),
            new MoveInfo(ACTOR_ID, new Point2D(0, 10), 90),
            new MoveInfo(ACTOR_ID, new Point2D(0, 10), new Point2D(-5, 10), 90, PEN_DEFAULT)
    ));
    private Map<String, String> impliedParameters = new HashMap<>() {{
        put("scale", "1");
    }};
    private Command north10 = new ValueDistance(List.of(new GenericValue(10d)));
    private Command left90 = new ValueTurnRelative(List.of(new GenericValue(90d)));
    private Command east5 = new ValueDistance(List.of(new GenericValue(-5d)));

    @BeforeEach
    void setup() {
        model = new Model(null);
        north10.setImpliedParameters(impliedParameters);
        left90.setImpliedParameters(impliedParameters);
        east5.setImpliedParameters(impliedParameters);
    }

    // Ensures the model starts with a world containing a single turtle at 0,0 and facing 0 degrees
    @Test
    void testModelInit() {
        assertNotNull(model.getWorld());
        assertEquals(model.getWorld().getActorCount(), 1);
        Actor actor = model.getWorld().getActorByIndex(0);
        assertNotNull(actor);
        assertEquals(actor.getClass(), Turtle.class);
        assertEquals(actor.getPosition(), new Point2D(0, 0));
        assertEquals(actor.getHeading(), 0);
    }

    @Test
    void testExecuteCommands() {
        List<MoveInfo> moveInfos = new ArrayList<>();

        // North 10
        assertDoesNotThrow(() -> moveInfos.addAll(model.executeCommand(north10)));
        assertP2DEquals(new Point2D(0, 10), model.getWorld().getActorByID(0).getPosition());
        assertEquals(0, model.getWorld().getActorByID(0).getHeading());
        // Left 90
        assertDoesNotThrow(() -> moveInfos.addAll(model.executeCommand(left90)));
        assertP2DEquals(new Point2D(0, 10), model.getWorld().getActorByID(0).getPosition());
        assertEquals(90, model.getWorld().getActorByID(0).getHeading());
        // East 5
        assertDoesNotThrow(() -> moveInfos.addAll(model.executeCommand(east5)));
        assertP2DEquals(new Point2D(-5, 10), model.getWorld().getActorByID(0).getPosition());
        assertEquals(90, model.getWorld().getActorByID(0).getHeading());

        assertEquals(expectedMoveInfos, moveInfos);
    }

    @Test
    void testExecuteManyCommands() {
        List<MoveInfo> moveInfos = new ArrayList<>();

        assertDoesNotThrow(() -> moveInfos.addAll(model.executeCommand(new CommandList(List.of(north10, left90, east5)))));
        assertP2DEquals(new Point2D(-5, 10), model.getWorld().getActorByID(0).getPosition());
        assertEquals(90d, model.getWorld().getActorByID(0).getHeading());

        assertEquals(expectedMoveInfos, moveInfos);
    }

    private void assertP2DEquals(Point2D pt1, Point2D pt2) {
        System.out.println("Testing: " + pt1 + " " + pt2);
        assertTrue(() -> Math.abs(pt1.distance(pt2)) < TOLERANCE);
    }

}
