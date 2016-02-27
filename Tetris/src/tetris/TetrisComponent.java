package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.EnumMap;
import java.util.Map;

public class TetrisComponent extends JComponent implements BoardListener {
    final Board gameBoard;
    private final static int SQUARE_SIZE = 40;
    private final static int PADDING = 5;

    private static final Map<SquareType, Color> colorMap =
            new EnumMap<SquareType, Color>(SquareType.class);

    public TetrisComponent(Board gameBoard) {
        this.gameBoard = gameBoard;

        // Setting up the colors
        colorMap.put(SquareType.EMPTY, Color.WHITE);
        colorMap.put(SquareType.I, new Color(255, 179, 71)); // Pastel Orange
        colorMap.put(SquareType.O, new Color(253, 253, 150)); // Pastel Yellow
        colorMap.put(SquareType.L, new Color(119, 158, 203)); // Dark Pastel Blue
        colorMap.put(SquareType.S, new Color(119, 190, 119)); // Pastel Green
        colorMap.put(SquareType.Z, new Color(255, 105, 97)); // Pastel Red
        colorMap.put(SquareType.T, new Color(100, 20, 100)); // Light Pastel
        colorMap.put(SquareType.J, new Color(255, 209, 220)); // Pastel Pink

        // Setting up key-bindings
        setupKeybindings();

    }

    public void setupKeybindings() {
        InputMap keybinds = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        keybinds.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "Right");
        keybinds.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "Left");

        ActionMap actions = getActionMap();
        actions.put("Right", new MoveRightAction());
        actions.put("Left", new MoveLeftAction());
    }


    private class MoveRightAction extends AbstractAction {
        @Override public void actionPerformed(final ActionEvent e) {
            while (gameBoard.getFalling() != null) {
                gameBoard.moveFallingRight();
            }
        }
    }

    private class MoveLeftAction extends AbstractAction {
        @Override public void actionPerformed(final ActionEvent e) {
            while (gameBoard.getFalling() != null) {
                gameBoard.moveFallingLeft();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D tile = (Graphics2D) g;

        for (int y = 0; y < gameBoard.getHeight(); y++) {
            for (int x = 0; x < gameBoard.getWidth(); x++) {
                if ((gameBoard.getFalling() != null) &&
                        ((gameBoard.getFallingX() <= x && (gameBoard.getFallingX() + gameBoard.getFalling().getHeight()) > x) &&
                                (gameBoard.getFallingY() <= y && gameBoard.getFallingY() + gameBoard.getFalling().getWidth() > y) &&
                                (gameBoard.getFalling().getSquareType(x - gameBoard.getFallingX(), y - gameBoard.getFallingY()) != SquareType.EMPTY))) {

                        tile.setColor(colorMap.get(gameBoard.getFalling().getSquareType(x - gameBoard.getFallingX(), y - gameBoard.getFallingY())));

                } else {
                    tile.setColor(colorMap.get(gameBoard.getSquareType(x, y)));
                }

                tile.fillRect(x * SQUARE_SIZE + PADDING, y * SQUARE_SIZE + PADDING,
                        SQUARE_SIZE - PADDING, SQUARE_SIZE - PADDING);
            }
        }
    }

    @Override
    public void boardChanged() {
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(gameBoard.getWidth() * SQUARE_SIZE + PADDING,
                            gameBoard.getHeight() * SQUARE_SIZE + PADDING);
    }
}
