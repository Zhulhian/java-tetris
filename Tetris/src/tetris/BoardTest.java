package tetris;
import javax.swing.*;
import java.awt.event.ActionEvent;

class BoardTest {

    public static final int WIDTH = 12;
    public static final int HEIGHT = 20;
    public static final int DELAY = 500;

    private static Timer clockTimer;

    private BoardTest() {}

    public static void main(String[] args) {

        Board gameBoard = new Board(WIDTH, HEIGHT + 1);
        TetrisFrame gameFrame = new TetrisFrame(gameBoard);
        HighscoreList highscore = HighscoreList.getInstance();

        final Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
    		    if (!gameBoard.isGameOver()) {
	        	    gameBoard.tick();
		        } else {
                    clockTimer.stop();
                    String name = JOptionPane.showInputDialog(null, "Enter name");
                    name = ((name == null) || (name.isEmpty())) ? name = "N/A" : name;
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
