package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class TetrisComponent extends JComponent implements BoardListener {
    private final Board gameBoard;
    public final static int SQUARE_SIZE = 40;
    private final static int PADDING = 5;
    private final static int FONT_SIZE = 32;

    final private Color bgCol;
    final private Color titleColor;
    final private Color fontColor;

    private Font dosFont;

    private static final Map<SquareType, Color> COLORMAP =
            new EnumMap<>(SquareType.class);

    public TetrisComponent(final Board gameBoard) {

	try {
	    this.dosFont = Font.createFont(Font.TRUETYPE_FONT, new File("dosvga.ttf"));
	    this.dosFont = this.dosFont.deriveFont(Font.TRUETYPE_FONT, FONT_SIZE);

	} catch (FontFormatException|IOException ex) {
	    System.out.println(ex);
	}

        this.gameBoard = gameBoard;

        // Setting up the colors
	this.titleColor = new Color(253, 253, 150);
        this.fontColor = new Color(253, 120, 120);
	this.bgCol = new Color(247, 247, 247);

        COLORMAP.put(SquareType.EMPTY, Color.WHITE);
        COLORMAP.put(SquareType.I, new Color(252, 177, 100)); // Pastel Orange
        COLORMAP.put(SquareType.O, new Color(255, 242, 102)); // Pastel Yellow
        COLORMAP.put(SquareType.L, new Color(100, 176, 252)); // Dark Pastel Blue
        COLORMAP.put(SquareType.S, new Color(90, 220, 130)); // Pastel Green
        COLORMAP.put(SquareType.Z, new Color(255, 105, 97)); // Pastel Red
        COLORMAP.put(SquareType.T, new Color(202, 150, 253)); // Pastel Purple
        COLORMAP.put(SquareType.J, new Color(255, 209, 220)); // Pastel Pink


        // Setting up key-bindings
        setupKeybindings();

    }

    public void setupKeybindings() {
        InputMap keybinds = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        keybinds.put(KeyStroke.getKeyStroke("UP"), "Rotate");
        keybinds.put(KeyStroke.getKeyStroke("RIGHT"), "Right");
        keybinds.put(KeyStroke.getKeyStroke("LEFT"), "Left");
        keybinds.put(KeyStroke.getKeyStroke("SPACE"), "Space");
        keybinds.put(KeyStroke.getKeyStroke("D"), "Debug");

        ActionMap actions = getActionMap();
        actions.put("Rotate", new RotateAction());
        actions.put("Right", new MoveRightAction());
        actions.put("Left", new MoveLeftAction());
        actions.put("Space", new SpaceAction());
	actions.put("Debug", new DebugAction());
    }

    private class DebugAction extends AbstractAction {
	@Override public void actionPerformed(final ActionEvent e) {
	    gameBoard.printFirstTwoColumn();
	}
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
                        tile.setColor(
				COLORMAP.get(gameBoard.getFalling().getSquareType(x - gameBoard.getFallingX(), y - gameBoard.getFallingY())));
                } else {
                    tile.setColor(COLORMAP.get(gameBoard.getSquareType(x, y)));
                }

                tile.fillRect(x * SQUARE_SIZE + PADDING, y * SQUARE_SIZE + PADDING,
                        SQUARE_SIZE - PADDING, SQUARE_SIZE - PADDING);
            }
        }

	tile.setColor(titleColor);
	tile.fillRect(0, 0, gameBoard.getWidth() * SQUARE_SIZE + PADDING, SQUARE_SIZE);

	tile.setColor(fontColor);
	tile.setFont(this.dosFont);
	tile.drawString("Score: " + Integer.toString(gameBoard.score), 5, FONT_SIZE - (SQUARE_SIZE - FONT_SIZE) / 2);

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
