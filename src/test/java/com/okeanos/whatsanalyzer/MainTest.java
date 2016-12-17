package com.okeanos.whatsanalyzer;

import org.junit.Ignore;
import org.junit.Test;

import java.security.InvalidParameterException;

/**
 * MainTest.
 */
public class MainTest {

    private static final String PATH = ClassLoader.getSystemResource("chat.txt").getPath();

    @Test
    public void testMainHappyCase() {
        Main.main(new String[]{PATH, "2015, 2016", "Okeanos", "Hyperion"});
    }

    @Test(expected = InvalidParameterException.class)
    public void testMainMissingParameters() {
        Main.main(new String[]{"1"});
    }

    @Test(expected = InvalidParameterException.class)
    public void testMainFileNotFound() {
        Main.main(new String[]{PATH + " text", "2015, 2016", "Okeanos", "Hyperion"});
    }

    @Test
    @Ignore
    public void testMainFileNotReadable() {
        // need static mocking of IOUtils,
        // see https://github.com/powermock/powermock
    }
}
