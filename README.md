# Bowling Score Calculator

This is a simple Java console program that calculates the total score of a bowling game. You just type the rolls (for example: 10 7 3 9 0 10 ...) and the program shows you each frame and the total score at the end. It understands strikes, spares, and also follows the special rules for the 10th frame.

To run the project, you can open it in an IDE like NetBeans, or if you prefer the terminal, use this command:

mvn compile exec:java -Dexec.mainClass="com.game.bowling.Main"

Example input:
10 7 3 9 0 10 0 8 8 2 0 6 10 10 10 8 1

Example output:

*** Frame Table Score ***
Frame 1: Frame([10])
Frame 2: Frame([7, 3])
Frame 3: Frame([9, 0])
Frame 4: Frame([10])
Frame 5: Frame([0, 8])
Frame 6: Frame([8, 2])
Frame 7: Frame([0, 6])
Frame 8: Frame([10])
Frame 9: Frame([10])
Frame 10: Frame([10, 8, 1])
Total Score: 167

That’s all :)
