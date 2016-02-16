package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class TetrisFrame extends JFrame {
    private final Board gameBoard;
    private final JTextArea textArea;

    public TetrisFrame(Board gameBoard) throws HeadlessException {
        super("Tetris Game");

        this.gameBoard = gameBoard;
        textArea = new JTextArea(gameBoard.getHeight(), gameBoard.getWidth());
        this.setLayout(new BorderLayout());

        createMenus();

        textArea.setText(BoardToTextConverter.convertToText(gameBoard));
        this.add(textArea, BorderLayout.CENTER);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));

        this.pack();
        this.setVisible(true);
    }

    public void setTextArea(String s) {
        this.textArea.setText(s);
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

    private class ExitListener implements ActionListener {
        public void actionPerformed(final ActionEvent e) {
            System.exit(0);
        }
    }
}
