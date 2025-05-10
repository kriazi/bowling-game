package com.game.bowling;

import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Main class.
 * Note: Since Main reads from System.in and writes to System.out,
 * we simulate user input and capture console output for testing.
 *
 * Author: Katayoun Riazi
 */
public class MainTest {

    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    /**
     * Test the full Main.main() execution with valid input.
     * This simulates a user entering a complete valid game.
     */
    @Test
    public void testMain_validInput() {
        // Simulate input for a typical game (score = 167)
        String input = "10 7 3 9 0 10 0 8 8 2 0 6 10 10 10 8 1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Run main method
        Main.main(new String[]{});

        String output = outContent.toString();

        // Check for expected output
        assertTrue(output.contains("Frame 1:"));
        assertTrue(output.contains("Total Score: 167"));
    }
}