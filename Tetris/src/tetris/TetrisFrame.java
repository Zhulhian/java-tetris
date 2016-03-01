package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class TetrisFrame extends JFrame {
    private final Board gameBoard;
    private final TetrisComponent tetrisComponent;
    private HighscoreComponent highscoreComponent;

    public TetrisFrame(Board gameBoard) throws HeadlessException {
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

        quit.addActionListener(new ExitListener());
        settings.add(quit);

        menu.add(settings);

        this.setJMenuBar(menu);
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
