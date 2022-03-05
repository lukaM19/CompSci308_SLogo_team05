package slogo.model;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActorTest {
    private static final double TOLERANCE = 0.00001;

    @Test
    void testTurtleMovement() {
        Turtle turtle = new Turtle(0);
        turtle.setPosition(new Point2D(0, 0));
        turtle.setHeading(0);
        turtle.move(10, 0); // North 10
        assertP2DEquals(new Point2D(0, 10), turtle.getPosition());
        assertEquals(0, turtle.getHeading());
        turtle.turn(Math.PI / 2); // Left 90
        assertP2DEquals(new Point2D(0, 10), turtle.getPosition());
        assertEquals(Math.PI / 2, turtle.getHeading());
        turtle.move(5, Math.PI); // East 5
        assertP2DEquals(new Point2D(-5, 10), turtle.getPosition());
        assertEquals(Math.PI / 2, turtle.getHeading());
        turtle.move(-5, -Math.PI / 2); // South 5
        assertP2DEquals(new Point2D(-5, 5), turtle.getPosition());
        assertEquals(Math.PI / 2, turtle.getHeading());
    }

    private void assertP2DEquals(Point2D pt1, Point2D pt2) {
        System.out.println("Testing: " + pt1 + " " + pt2);
        assertTrue(() -> Math.abs(pt1.distance(pt2)) < TOLERANCE);
    }
}
