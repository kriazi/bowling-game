package com.game.bowling.service;

import com.game.bowling.model.Frame;
import com.game.bowling.model.Roll;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ScoringService class.
 * These tests verify the correctness of frame construction and score calculation
 * for various standard and edge-case bowling scenarios.
 *
 * Author: Katayoun Riazi
 */
public class ScoringServiceTest {

    private final ScoringService service = new ScoringService();

    /**
     * Test that buildFrames correctly constructs exactly 10 frames from a valid list of rolls.
     */
    @Test
    public void testBuildFrames_correctNumberOfFrames() {
        List<Roll> rolls = List.of(
            10, 7, 3, 9, 0, 10, 0, 8, 8, 2, 0, 6, 10, 10, 10, 8, 1
        ).stream().map(Roll::new).collect(Collectors.toList());

        List<Frame> frames = service.buildFrames(rolls);

        assertEquals(10, frames.size(), "Should create exactly 10 frames");
    }

    /**
     * Test scoring logic for a typical game with a mix of strikes, spares, and open frames.
     */
    @Test
    public void testCalculateScore_typicalGame() {
        List<Roll> rolls = List.of(
            10, 7, 3, 9, 0, 10, 0, 8, 8, 2, 0, 6, 10, 10, 10, 8, 1
        ).stream().map(Roll::new).collect(Collectors.toList());

        List<Frame> frames = service.buildFrames(rolls);
        int result = service.calculateScore(frames);

        assertEquals(167, result, "Expected score is 167");
    }

    /**
     * Test scoring logic for a perfect game (12 consecutive strikes).
     */
    @Test
    public void testCalculateScore_perfectGame() {
        List<Roll> rolls = IntStream.generate(() -> 10)
                                    .limit(12)
                                    .mapToObj(Roll::new)
                                    .collect(Collectors.toList());

        List<Frame> frames = service.buildFrames(rolls);
        int result = service.calculateScore(frames);

        assertEquals(300, result, "Perfect game should score 300");
    }

    /**
     * Test scoring when all frames are spares with a 5 as the bonus roll.
     */
    @Test
    public void testCalculateScore_allSpares() {
        List<Roll> rolls = IntStream.range(0, 21)
                                    .map(i -> 5)
                                    .mapToObj(Roll::new)
                                    .collect(Collectors.toList());

        List<Frame> frames = service.buildFrames(rolls);
        int result = service.calculateScore(frames);

        assertEquals(150, result, "All spares with 5 bonus should score 150");
    }

    /**
     * Test scoring when there are no strikes or spares — just open frames.
     */
    @Test
    public void testCalculateScore_noStrikesOrSpares() {
        List<Roll> rolls = List.of(
            3, 4, 5, 2, 1, 1, 0, 6, 2, 3, 3, 3, 4, 2, 5, 1, 2, 1, 3, 4
        ).stream().map(Roll::new).collect(Collectors.toList());

        List<Frame> frames = service.buildFrames(rolls);
        int result = service.calculateScore(frames);

        assertEquals(55, result, "No spares/strikes, should return basic sum");
    }
}