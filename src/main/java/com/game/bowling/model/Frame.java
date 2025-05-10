package com.game.bowling.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single frame in a bowling game.
 * A frame typically contains one or two rolls, but the 10th frame may include a third roll if a strike or spare is scored.
 *
 * Author: Katayoun Riazi
 */
public class Frame {

    // The list of rolls in this frame
    private final List<Roll> rolls;

    /**
     * Constructs a Frame with a list of rolls.
     * Defensive copy is made to protect internal state.
     *
     * @param rolls the list of rolls in this frame
     */
    public Frame(List<Roll> rolls) {
        this.rolls = new ArrayList<>(rolls); 
    }

    /**
     * Determines if the frame is a strike.
     * A strike occurs when the first roll knocks down all 10 pins.
     *
     * @return true if strike, false otherwise
     */
    public boolean isStrike() {
        return !rolls.isEmpty() && rolls.get(0).getValue() == 10;
    }

    /**
     * Determines if the frame is a spare.
     * A spare occurs when two rolls knock down all 10 pins (but not a strike).
     *
     * @return true if spare, false otherwise
     */
    public boolean isSpare() {
        return rolls.size() >= 2 &&
               !isStrike() && 
               (rolls.get(0).getValue() + rolls.get(1).getValue() == 10);
    }

    /**
     * Returns a copy of the list of rolls.
     * Prevents external modification of internal state.
     *
     * @return a new list containing all rolls
     */
    public List<Roll> getRolls() {
        return new ArrayList<>(rolls); 
    }

    /**
     * Gets the value of the first roll, or 0 if not available.
     *
     * @return value of the first roll
     */
    public int getFirstRollValue() {
        return !rolls.isEmpty() ? rolls.get(0).getValue() : 0;
    }

    /**
     * Gets the value of the second roll, or 0 if not available.
     *
     * @return value of the second roll
     */
    public int getSecondRollValue() {
        return rolls.size() > 1 ? rolls.get(1).getValue() : 0;
    }

    /**
     * Gets the value of the optional third roll (used in 10th frame), or 0 if not available.
     *
     * @return value of the third roll
     */
    public int getThirdRollValue() {
        return rolls.size() > 2 ? rolls.get(2).getValue() : 0;
    }

    /**
     * Returns a string representation of the frame.
     *
     * @return string in the format "Frame([x, y, z])"
     */
    @Override
    public String toString() {
        return "Frame(" + getRolls() + ")";
    }
}
