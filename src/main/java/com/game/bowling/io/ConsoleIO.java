package com.game.bowling.io;

import com.game.bowling.model.Frame;
import com.game.bowling.model.Roll;
import com.game.bowling.service.ScoringService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ConsoleIO handles interaction with the user through the command-line interface.
 * It reads user input (roll values) and displays the final result including each frame and total score.
 *
 * This version is designed to support clean testability by allowing scanner injection
 * and validating input until a complete game is received.
 *
 * Author: Katayoun Riazi
 */
public class ConsoleIO {

    /**
     * Reads roll values from a provided Scanner instance.
     * Accepts space-separated integers between 0 and 10 and filters out invalid values.
     *
     * @param scanner a Scanner object (can be System.in or test input)
     * @return a list of valid Roll objects
     */
    public List<Roll> readRolls(Scanner scanner) {
        System.out.println("Enter bowling rolls separated by spaces:");
        String input = scanner.nextLine();

        List<Roll> rolls = new ArrayList<>();

        for (String number : input.trim().split("\\s+")) {
            try {
                int value = Integer.parseInt(number);
                if (value < 0 || value > 10) {
                    System.out.println("Invalid roll value (must be 0-10): " + value);
                    continue;
                }
                rolls.add(new Roll(value));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, expected a number: " + number);
            }
        }

        return rolls;
    }

    /**
     * Continuously prompts the user until a valid and complete list of rolls
     * representing 10 full frames is received.
     * Validates structure and the special rules of the 10th frame (extra rolls if needed).
     *
     * @return a list of valid Roll objects that form a complete bowling game
     */
    public List<Roll> readValidRolls() {
        Scanner scanner = new Scanner(System.in);  // shared scanner for consistent input
        List<Roll> rolls;

        while (true) {
            rolls = readRolls(scanner); // read input once

            ScoringService service = new ScoringService();
            List<Frame> frames = service.buildFrames(rolls);

            if (frames.size() < 10) {
                System.out.println("Incomplete input: please enter enough rolls to complete 10 frames.");
            } else if (!isValidTenthFrame(frames.get(9))) {
                System.out.println("Invalid 10th frame: ensure it includes 2 or 3 rolls based on the rules.");
            } else {
                break; // input is valid and complete
            }
        }

        return rolls;
    }

    /**
     * Checks whether the 10th frame is valid based on bowling rules.
     * - If it's a strike or spare, it must contain 3 rolls.
     * - Otherwise, it must contain exactly 2 rolls.
     *
     * @param frame the 10th frame
     * @return true if valid, false otherwise
     */
    private boolean isValidTenthFrame(Frame frame) {
        int rollCount = frame.getRolls().size();

        if (frame.isStrike() || frame.isSpare()) {
            return rollCount == 3;
        } else {
            return rollCount == 2;
        }
    }

    /**
     * Prints each frame and the total score to the console in a readable format.
     *
     * @param frames      the list of frames in the game
     * @param totalScore  the total calculated score
     */
    public void writeScore(List<Frame> frames, int totalScore) {
        System.out.println("\n*** Frame Table Score ***");
        for (int i = 0; i < frames.size(); i++) {
            System.out.println("Frame " + (i + 1) + ": " + frames.get(i));
        }
        System.out.println("Total Score: " + totalScore);
    }
}