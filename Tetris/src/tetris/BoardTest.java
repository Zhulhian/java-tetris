package tetris;
import javax.swing.*;
import java.awt.event.ActionEvent;

class BoardTest {

    private static final int WIDTH = 20;
    private static final int HEIGHT = 35;

    public static void main(String[] args) {

        Board gameBoard = new Board(WIDTH, HEIGHT);
        TetrisFrame gameFrame = new TetrisFrame(gameBoard);

        final Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                gameBoard.tick();
            }
        };

        final Timer clockTimer = new Timer(500, doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();

        //clockTimer.stop();
    }
}
