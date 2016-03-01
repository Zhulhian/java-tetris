package tetris;
import javax.swing.Timer;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

class BoardTest {

    private static final int WIDTH = 12;
    private static final int HEIGHT = 20;
    public static final int DELAY = 500;

    private static Timer clockTimer;

    private BoardTest() {}

    public static void main(String[] args) {

        Board gameBoard = new Board(WIDTH, HEIGHT + 1);
        TetrisFrame gameFrame = new TetrisFrame(gameBoard);

        final Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
		if (!gameBoard.isGameOver()) {
		    gameBoard.tick();
		} else {

		}
            }
        };

        clockTimer = new Timer(DELAY, doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();

        //clockTimer.stop();
    }
}
