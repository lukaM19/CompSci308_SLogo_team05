package slogo.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorldTest {
    World myWorld;

    @BeforeEach
    void setup() {
        myWorld = new World();
    }
    // This test will check that the AddActor() method and getActorCount() method work
    @Test
    void TestActorCountandAddActor() {
        Actor turtle = new Actor(0);
        myWorld.addActor(turtle);
        Assertions.assertEquals(1, myWorld.getActorCount());
        Actor turtle2 = new Actor(1);
        myWorld.addActor(turtle2);
        Assertions.assertEquals(2, myWorld.getActorCount());
    }

    @Test
    void TestgetActor() {
        double TURTLE_ID = 0;
        Actor turtle = new Actor(TURTLE_ID);
        myWorld.addActor(turtle);
        Assertions.assertEquals(turtle, myWorld.getActorByIndex(0));
        Assertions.assertEquals(turtle, myWorld.getActorByID(TURTLE_ID));
    }

    @Test
    void TestRemoveActorMethods() {
        Actor turtle = new Actor(1);
        Actor turtle2 = new Actor(2);
        myWorld.addActor(turtle);
        myWorld.addActor(turtle2);
        Assertions.assertEquals(2, myWorld.getActorCount());
        myWorld.removeActorAt(1);
        Assertions.assertEquals(1, myWorld.getActorCount());
        myWorld.removeActor(turtle);
        Assertions.assertEquals(0, myWorld.getActorCount());
    }
}
