package com.game.bowling.service;

import com.game.bowling.model.Frame;
import com.game.bowling.model.Roll;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for processing bowling game data.
 * It builds frames from raw rolls and calculates the total score based on bowling rules.
 *
 * Author: Katayoun Riazi
 */
public class ScoringService {

    /**
     * Converts a list of rolls into a list of 10 frames according to bowling rules.
     * Handles strike (1 roll), spare/normal (2 rolls), and special 10th frame with up to 3 rolls.
     *
     * @param allRolls the complete list of rolls entered by the user
     * @return list of up to 10 Frame objects
     */
    public List<Frame> buildFrames(List<Roll> allRolls) {
        List<Frame> frames = new ArrayList<>();
        int i = 0;

        for (int frame = 0; frame < 10 && i < allRolls.size(); frame++) {
            // Handle strike
            if (allRolls.get(i).getValue() == 10) {
                if (frame == 9 && i + 2 < allRolls.size()) {
                    // Special case: strike in 10th frame => up to 3 rolls
                    frames.add(new Frame(allRolls.subList(i, i + 3)));
                    i += 3;
                } else {
                    frames.add(new Frame(List.of(allRolls.get(i))));
                    i += 1;
                }
            } else {
                // Handle normal or spare frame
                if (frame == 9 && i + 2 < allRolls.size()) {
                    // Special case: spare/normal in 10th frame
                    frames.add(new Frame(allRolls.subList(i, i + 3)));
                    i += 3;
                } else if (i + 1 < allRolls.size()) {
                    frames.add(new Frame(List.of(allRolls.get(i), allRolls.get(i + 1))));
                    i += 2;
                }
            }
        }

        return frames;
    }

    /**
     * Calculates the total score from a list of frames.
     * Applies strike and spare bonuses according to standard bowling rules.
     *
     * @param frames the list of frames created from rolls
     * @return total game score
     */
    public int calculateScore(List<Frame> frames) {
        int total = 0;

        for (int i = 0; i < frames.size(); i++) {
            Frame frame = frames.get(i);

            if (frame.isStrike()) {
                total += 10 + getNextTwoRollValues(frames, i);
            } else if (frame.isSpare()) {
                total += 10 + getNextOneRollValue(frames, i);
            } else {
                total += frame.getFirstRollValue() + frame.getSecondRollValue();
            }
        }

        return total;
    }

    /**
     * Returns the value of the next roll following the given frame.
     * Used for calculating the bonus of a spare.
     *
     * @param frames  the list of all frames
     * @param current the index of the current frame
     * @return value of the next roll or 0 if not available
     */
    private int getNextOneRollValue(List<Frame> frames, int current) {
        if (current + 1 < frames.size()) {
            List<Roll> nextRolls = frames.get(current + 1).getRolls();
            if (!nextRolls.isEmpty()) {
                return nextRolls.get(0).getValue();
            }
        }

        // fallback: in case of 10th frame with bonus roll
        List<Roll> currentRolls = frames.get(current).getRolls();
        if (currentRolls.size() >= 3) {
            return currentRolls.get(2).getValue();
        }

        return 0;
    }

    /**
     * Returns the sum of the next two rolls following the given frame.
     * Used for calculating the bonus of a strike.
     *
     * @param frames  the list of all frames
     * @param current the index of the current frame
     * @return sum of the next two roll values
     */
    private int getNextTwoRollValues(List<Frame> frames, int current) {
        List<Integer> values = new ArrayList<>();

        // Collect next rolls from upcoming frames
        for (int i = current + 1; i < frames.size() && values.size() < 2; i++) {
            for (Roll r : frames.get(i).getRolls()) {
                values.add(r.getValue());
                if (values.size() == 2) break;
            }
        }

        // fallback: grab additional rolls from current frame (useful for 10th frame)
        if (values.size() < 2) {
            List<Roll> currentRolls = frames.get(current).getRolls();
            for (int j = 1; j < currentRolls.size(); j++) {
                values.add(currentRolls.get(j).getValue());
                if (values.size() == 2) break;
            }
        }

        return values.stream().mapToInt(Integer::intValue).sum();
    }
}