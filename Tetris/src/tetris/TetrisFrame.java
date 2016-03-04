package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Extends JFrame. Handles the window management, as well as restarting
 * the game and showing the highscore and tetris components.
 */
class TetrisFrame extends JFrame {
    private final Board gameBoard;
    private TetrisComponent tetrisComponent;
    // Might not get initialized during object construction, but it gets initialized
    // when it is used, in the showHighscore function, since it get's the highscore
    // list from there.
    private HighscoreComponent highscoreComponent;

    TetrisFrame(Board gameBoard) throws HeadlessException {
        super(" / T E T R I S / ");

        this.gameBoard = gameBoard;
        this.setLayout(new BorderLayout());
        createMenus();

        tetrisComponent = new TetrisComponent(gameBoard);
        gameBoard.addBoardListener(tetrisComponent);
        this.add(tetrisComponent);

        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void createMenus() {
        final JMenuBar menu = new JMenuBar();

        final JMenu settings = new JMenu("Settings");
        final JMenuItem quit = new JMenuItem("Quit");
        final JMenuItem reset = new JMenuItem("Reset");

        reset.addActionListener(e -> resetGame());

        quit.addActionListener(new ExitListener());
        settings.add(quit);
        settings.add(reset);

        menu.add(settings);

        this.setJMenuBar(menu);
    }

    private void resetGame() {
	    // If the game is running, i.e. the highscoreComponent hasn't
	    // been created yet, remove the tetris component. If it does
	    // exist remove it.
        if (highscoreComponent != null) {
            this.remove(highscoreComponent);
        } else {
            this.remove(tetrisComponent);
        }

	    // Reset the gameboard, then create a new tetrisComponent from
	    // that fresh board.
        gameBoard.resetBoard();
        tetrisComponent = new TetrisComponent(gameBoard);
        gameBoard.addBoardListener(tetrisComponent);

	    // Start the clocktimer.
        BoardTest.clockTimer.start();

	    // Add the component to the frame and pack it.
        this.add(tetrisComponent);
        this.pack();
    }


    public void showHighscore(HighscoreList hs) {
	    // Make a new highscore component and remove the tetris component
        highscoreComponent = new HighscoreComponent(hs);
        this.remove(tetrisComponent);
        this.add(highscoreComponent);
        this.pack();
    }

	// Handles what happens when the exit button is pressed.
    private class ExitListener implements ActionListener {
        public void actionPerformed(final ActionEvent e) {
            System.exit(0);
        }
    }
}
