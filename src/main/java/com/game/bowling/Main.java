package com.game.bowling;

import com.game.bowling.io.ConsoleIO;
import com.game.bowling.model.Frame;
import com.game.bowling.model.Roll;
import com.game.bowling.service.ScoringService;

import java.util.List;

/**
 * Entry point for the Bowling Score Calculator application.
 * This class coordinates the input/output operations and the core logic.
 *
 * Author: Katayoun Riazi
 */
public class Main {

    private final ConsoleIO io;
    private final ScoringService scoringService;

    /**
     * Constructs the Main class with provided IO and scoring services.
     * This allows for easy dependency injection and better testability.
     *
     * @param io              the ConsoleIO instance for user interaction
     * @param scoringService  the ScoringService instance for scoring logic
     */
    public Main(ConsoleIO io, ScoringService scoringService) {
        this.io = io;
        this.scoringService = scoringService;
    }

    /**
     * Orchestrates the game process
     */
    public void run() {
        List<Roll> rolls = io.readValidRolls();                    // Read valid roll input
        List<Frame> frames = scoringService.buildFrames(rolls);    // Build frames from rolls
        int totalScore = scoringService.calculateScore(frames);    // Calculate score
        io.writeScore(frames, totalScore);                         // Display the score
    }

    /**
     * Main method: application entry point.
     * Initializes dependencies and starts the application logic.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        ConsoleIO io = new ConsoleIO();                        // Create input/output handler
        ScoringService scoringService = new ScoringService();  // Create scoring service
        Main app = new Main(io, scoringService);               // Inject dependencies
        app.run();                                             // Run the application
    }
}