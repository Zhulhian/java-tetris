package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class TetrisFrame extends JFrame {
    private final Board gameBoard;
    private TetrisComponent tetrisComponent;
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
        this.remove(highscoreComponent);
        gameBoard.resetBoard();
        tetrisComponent = new TetrisComponent(gameBoard);
        gameBoard.addBoardListener(tetrisComponent);
        BoardTest.clockTimer.start();
        this.add(tetrisComponent);
        this.pack();
    }


    public void showHighscore(HighscoreList hs) {
        highscoreComponent = new HighscoreComponent(hs);
        this.remove(tetrisComponent);
        this.add(highscoreComponent);
        this.pack();
    }

    private class ExitListener implements ActionListener {
        public void actionPerformed(final ActionEvent e) {
            System.exit(0);
        }
    }
}
