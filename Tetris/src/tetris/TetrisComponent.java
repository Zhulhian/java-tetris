package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.EnumMap;
import java.util.Map;

public class TetrisComponent extends JComponent implements BoardListener {
    private final Board gameBoard;
    public final static int SQUARE_SIZE = 40;
    private final static int PADDING = 5;

    final Color bgCol;

    private static final Map<SquareType, Color> colorMap =
            new EnumMap<>(SquareType.class);

    public TetrisComponent(final Board gameBoard) {
        this.gameBoard = gameBoard;

        // Setting up the colors
        this.bgCol = new Color(247, 247, 247);
        colorMap.put(SquareType.EMPTY, Color.WHITE);
        colorMap.put(SquareType.I, new Color(252, 177, 100)); // Pastel Orange // 255, 179, 71
        colorMap.put(SquareType.O, new Color(255, 242, 102)); // Pastel Yellow
        colorMap.put(SquareType.L, new Color(100, 176, 252)); // Dark Pastel Blue 119, 158, 203
        colorMap.put(SquareType.S, new Color(90, 220, 130)); // Pastel Green
        colorMap.put(SquareType.Z, new Color(255, 105, 97)); // Pastel Red
        colorMap.put(SquareType.T, new Color(202, 150, 253)); // Pastel Purple
        colorMap.put(SquareType.J, new Color(255, 209, 220)); // Pastel Pink

        // Setting up key-bindings
        setupKeybindings();

    }

    public void setupKeybindings() {
        InputMap keybinds = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        keybinds.put(KeyStroke.getKeyStroke("UP"), "Rotate");
        keybinds.put(KeyStroke.getKeyStroke("RIGHT"), "Right");
        keybinds.put(KeyStroke.getKeyStroke("LEFT"), "Left");
        keybinds.put(KeyStroke.getKeyStroke("SPACE"), "Space");

        ActionMap actions = getActionMap();
        actions.put("Rotate", new RotateAction());
        actions.put("Right", new MoveRightAction());
        actions.put("Left", new MoveLeftAction());
        actions.put("Space", new SpaceAction());
    }

    private class SpaceAction extends AbstractAction {
        @Override public void actionPerformed(final ActionEvent e) {
            while (gameBoard.getFalling() != null) {
                gameBoard.fall();
            }
        }
    }

    private class MoveRightAction extends AbstractAction {
        @Override public void actionPerformed(final ActionEvent e) {
            if (gameBoard.getFalling() != null) {
                gameBoard.moveFallingRight();
            }
        }
    }

    private class MoveLeftAction extends AbstractAction {
        @Override public void actionPerformed(final ActionEvent e) {
            if (gameBoard.getFalling() != null) {
                gameBoard.moveFallingLeft();
            }
        }
    }

    private class RotateAction extends AbstractAction {
        @Override public void actionPerformed(final ActionEvent e) {
            if (gameBoard.getFalling() != null) {
                gameBoard.rotate();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D tile = (Graphics2D) g;

        // Setting and drawing the background/line color.
        tile.setColor(bgCol);
        tile.fillRect(0, 0, gameBoard.getWidth() * SQUARE_SIZE + PADDING,
                            gameBoard.getHeight() * SQUARE_SIZE + PADDING);

        for (int y = 0; y < gameBoard.getHeight(); y++) {
            for (int x = 0; x < gameBoard.getWidth(); x++) {
                if (drawFallingAt(x, y)) {
                        tile.setColor(colorMap.get(gameBoard.getFalling().getSquareType(x - gameBoard.getFallingX(), y - gameBoard.getFallingY())));
                } else {
                    tile.setColor(colorMap.get(gameBoard.getSquareType(x, y)));
                }

                tile.fillRect(x * SQUARE_SIZE + PADDING, y * SQUARE_SIZE + PADDING,
                        SQUARE_SIZE - PADDING, SQUARE_SIZE - PADDING);
            }
        }
    }

    private boolean drawFallingAt(int x, int y) {
        return ((gameBoard.getFalling() != null) &&
                ((gameBoard.getFallingX() <= x && (gameBoard.getFallingX() + gameBoard.getFalling().getHeight()) > x) &&
                        (gameBoard.getFallingY() <= y && gameBoard.getFallingY() + gameBoard.getFalling().getWidth() > y) &&
                    (gameBoard.getFalling().getSquareType(x - gameBoard.getFallingX(), y - gameBoard.getFallingY()) != SquareType.EMPTY)));

        }

    @Override public void boardChanged() {
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(gameBoard.getWidth() * SQUARE_SIZE + PADDING,
                            gameBoard.getHeight() * SQUARE_SIZE + PADDING);
    }
}
