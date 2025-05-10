package com.game.bowling.model;

/**
 * Represents a single roll in a bowling game.
 * Each roll holds an integer value between 0 and 10 inclusive.
 *
 * Author: Katayoun Riazi
 */
public class Roll {

    // Number of pins knocked down in this roll
    private final int value;

    /**
     * Constructs a Roll with the specified value.
     *
     * @param value the number of pins knocked down (expected range: 0–10)
     */
    public Roll(int value) {
        this.value = value;
    }

    /**
     * Returns the number of pins knocked down in this roll.
     *
     * @return value of the roll
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns a string representation of the roll.
     *
     * @return string version of the roll's value
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
