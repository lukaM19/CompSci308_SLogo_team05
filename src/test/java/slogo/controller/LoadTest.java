package slogo.controller;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Collection;
import java.util.List;

public class LoadTest {
    private LogoLoader myLoader;
    private String TEST_FILE_PATH = "/Users/luke/Desktop/CompSci/CS308/slogo_team05/data/XMLfiles/Test1.xml";

    @BeforeEach
    void setUp() {
        myLoader = new LogoLoader();
    }

    @Test
    void testLoader() {
        try {
            Collection<String> command = myLoader.loadLogo(new File(TEST_FILE_PATH));
            assertEquals("rt 90", command.toArray()[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
