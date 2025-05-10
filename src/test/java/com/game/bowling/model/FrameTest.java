package com.game.bowling.model;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Frame class in the bowling game.
 * These tests validate the behavior of identifying strikes and spares,
 * retrieving roll values, and verifying string representation.
 *
 * Author: Katayoun Riazi
 */
public class FrameTest {

    /**
     * Test that a frame with a single roll of 10 is correctly identified as a strike.
     */
    @Test
    public void testIsStrike_whenStrike() {
        Frame frame = new Frame(List.of(new Roll(10)));
        assertTrue(frame.isStrike());
    }

    /**
     * Test that a frame with two rolls totaling 10 is not a strike.
     */
    @Test
    public void testIsStrike_whenNotStrike() {
        Frame frame = new Frame(List.of(new Roll(4), new Roll(6)));
        assertFalse(frame.isStrike());
    }

    /**
     * Test that a frame with two rolls totaling 10 (but not a strike) is correctly identified as a spare.
     */
    @Test
    public void testIsSpare_whenSpare() {
        Frame frame = new Frame(List.of(new Roll(7), new Roll(3)));
        assertTrue(frame.isSpare());
    }

    /**
     * Test that a frame with two rolls totaling less than 10 is not a spare.
     */
    @Test
    public void testIsSpare_whenNotSpare() {
        Frame frame = new Frame(List.of(new Roll(4), new Roll(4)));
        assertFalse(frame.isSpare());
    }

    /**
     * Test that getRolls returns the correct rolls and values in order.
     */
    @Test
    public void testGetRolls() {
        Roll r1 = new Roll(4);
        Roll r2 = new Roll(6);
        Frame frame = new Frame(List.of(r1, r2));
        List<Roll> result = frame.getRolls();

        assertEquals(2, result.size());
        assertEquals(4, result.get(0).getValue());
        assertEquals(6, result.get(1).getValue());
    }

    /**
     * Test that getFirstRollValue returns the correct value.
     */
    @Test
    public void testGetFirstRollValue() {
        Frame frame = new Frame(List.of(new Roll(8)));
        assertEquals(8, frame.getFirstRollValue());
    }

    /**
     * Test that getSecondRollValue returns the correct value when present.
     */
    @Test
    public void testGetSecondRollValue() {
        Frame frame = new Frame(List.of(new Roll(3), new Roll(6)));
        assertEquals(6, frame.getSecondRollValue());
    }

    /**
     * Test that getThirdRollValue returns the correct value when present (e.g. in 10th frame).
     */
    @Test
    public void testGetThirdRollValue_whenPresent() {
        Frame frame = new Frame(List.of(new Roll(10), new Roll(10), new Roll(10)));
        assertEquals(10, frame.getThirdRollValue());
    }

    /**
     * Test that getThirdRollValue returns 0 when a third roll is not present.
     */
    @Test
    public void testGetThirdRollValue_whenMissing() {
        Frame frame = new Frame(List.of(new Roll(10)));
        assertEquals(0, frame.getThirdRollValue());
    }

    /**
     * Test the string output of the Frame class.
     */
    @Test
    public void testToString() {
        Frame frame = new Frame(List.of(new Roll(4), new Roll(5)));
        String str = frame.toString();
        assertTrue(str.contains("Frame([4, 5])"));
    }
}
