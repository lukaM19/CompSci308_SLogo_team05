package slogo.controller;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Collection;
import java.util.List;

public class SaveLoadTest {
    private LogoSaver mySaver;
    private LogoLoader myLoader;

    @BeforeEach
    void setUp() {
        mySaver = new LogoSaver();
        myLoader = new LogoLoader();
    }

    @Test
    void testLoader() {
        try {
            Collection<String> command = myLoader.loadLogo(new File("/Users/username/Desktop/test1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //AssertEquals.
    }
}
