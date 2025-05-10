package com.game.bowling.io;

import com.game.bowling.model.Frame;
import com.game.bowling.model.Roll;
import com.game.bowling.service.ScoringService;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ConsoleIO class.
 * These tests validate input parsing and output formatting in a bowling game.
 *
 * Author: Katayoun Riazi
 */
public class ConsoleIOTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    /**
     * Test the readRolls(Scanner) method with valid simulated input.
     */
    @Test
    public void testReadRolls_validInput() {
        String input = "10 4 5\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        ConsoleIO io = new ConsoleIO();
        List<Roll> rolls = io.readRolls(scanner);

        assertEquals(3, rolls.size());
        assertEquals(10, rolls.get(0).getValue());
        assertEquals(5, rolls.get(2).getValue());
    }

    /**
     * Test the readValidRolls() method with complete valid input.
     * Ensures the loop exits when 10 valid frames are built.
     */
    @Test
    public void testReadValidRolls_validGame() {
        String input = "10 7 3 9 0 10 0 8 8 2 0 6 10 10 10 8 1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ConsoleIO io = new ConsoleIO();
        List<Roll> rolls = io.readValidRolls();

        ScoringService scoringService = new ScoringService();
        List<Frame> frames = scoringService.buildFrames(rolls);

        assertEquals(10, frames.size());
        assertEquals(167, scoringService.calculateScore(frames));
    }

    /**
     * Test the writeScore() method and check that formatted output is printed.
     */
    @Test
    public void testWriteScore_outputFormat() {
        ConsoleIO io = new ConsoleIO();

        List<Roll> rolls = List.of(new Roll(10), new Roll(8), new Roll(1));
        Frame frame = new Frame(rolls);
        io.writeScore(List.of(frame), 19);

        String output = outContent.toString();
        assertTrue(output.contains("Frame 1: Frame([10, 8, 1])"));
        assertTrue(output.contains("Total Score: 19"));
    }
}