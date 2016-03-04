package tetris;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * The main class that runs the game.
 */
final class BoardTest {

	// Game board size dimensions
	public static final int WIDTH = 14;
	public static final int HEIGHT = 20;

	// Delay between each tick.
	private static final int DELAY = 500;

	// I make the clockTimer public so it can be stopped and started
	// in the tetrisFrame. Initialized to null. Is later initialized to a new Timer.
	public static Timer clockTimer = null;

	private BoardTest() {}

	public static void main(String[] args) {

		Board gameBoard = new Board(WIDTH, HEIGHT);
		TetrisFrame gameFrame = new TetrisFrame(gameBoard);
		HighscoreList highscore = HighscoreList.getInstance();

		// Each DELAY milliseconds do:
		final Action doOneStep = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				// If the game isn't over, proceed one tick
				if (gameBoard.isGameRunning()) {
					gameBoard.tick();
				} else {
					// If game is over, stop the clock, ask for name and add
					// the score to the highscore list and sort it.
					clockTimer.stop();
					String name = JOptionPane.showInputDialog(null, "Enter name");
					// If no name is stated, or cancel pressed, set the name to N/A.
					if (name == null || name.isEmpty()) {
						name = "N/A";
					}
					highscore.addHighscore(name, gameBoard.score);
					highscore.sort();

					gameFrame.showHighscore(highscore);

				}
			}
		};

		clockTimer = new Timer(DELAY, doOneStep);
		clockTimer.setCoalesce(true);
		clockTimer.start();
	}


}
