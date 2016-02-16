package tetris;
import javax.swing.*;
import java.awt.event.ActionEvent;

class BoardTest {
    private static Board gameBoard;
    private static TetrisFrame gameFrame;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 35;

    public static void main(String[] args) {
        gameBoard = new Board(WIDTH, HEIGHT);
        gameFrame = new TetrisFrame(gameBoard);

        final Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                gameBoard.randomizeBoard();
                gameFrame.setTextArea(BoardToTextConverter.convertToText(gameBoard));
            }
        };

        final Timer clockTimer = new Timer(100, doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();

        //clockTimer.stop();
    }
}
