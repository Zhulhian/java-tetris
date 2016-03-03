package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class TetrisComponent extends JComponent implements BoardListener {
    private final Board gameBoard;
    public final static int SQUARE_SIZE = 40;
    private final static int BAR_HEIGHT = 40;
    private final static int SFX_SIZE = 5;
    private final static int PADDING = 5;
    private final static int FONT_SIZE = 32;
	//private final static int PAUSE_FONT_SIZE = 64;


    final private Color bgCol;

	// Color for the pause screen, when/if I implement it.
	//final private Color pauseBG;

    // Fallthrough special block background & foreground color
    final private Color fallthruBG;
    final private Color fallthruFG;

    // Phase special block foreground & background color
    final private Color phaseBG;
    final private Color phaseFG;

    final private Color barColor;
    final private Color fontColor;
    final private Color sfxFontCol;

    private Font dosFont;

	// Pause screen font, for when/if I implement it.
	//private final Font pauseFont;

    private static final Map<SquareType, Color> COLORMAP =
            new EnumMap<>(SquareType.class);

    public TetrisComponent(final Board gameBoard) {

        try {
            this.dosFont = Font.createFont(Font.TRUETYPE_FONT, new File("dosvga.ttf"));
            this.dosFont = this.dosFont.deriveFont(Font.TRUETYPE_FONT, FONT_SIZE);

        } catch (FontFormatException|IOException ex) {
            System.out.println(ex);
        }

	    //pauseFont = dosFont.deriveFont(Font.BOLD, PAUSE_FONT_SIZE);

        this.gameBoard = gameBoard;

        // Setting up the colors
        this.barColor = new Color(253, 253, 150);
        this.fontColor = new Color(253, 120, 120);
        this.sfxFontCol = new Color(150, 150, 253);

        this.fallthruBG = new Color(253, 189, 125);
        this.fallthruFG = new Color(253, 125, 125);

        this.phaseFG = new Color(200, 227, 254);
        this.phaseBG = new Color(150, 202, 253);

        this.bgCol = new Color(247, 247, 247);
	    //this.pauseBG = new Color(120, 253, 187);


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

    private void setupKeybindings() {
        InputMap keybinds = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        keybinds.put(KeyStroke.getKeyStroke("UP"), "Up");
        keybinds.put(KeyStroke.getKeyStroke("DOWN"), "Down");
        keybinds.put(KeyStroke.getKeyStroke("RIGHT"), "Right");
        keybinds.put(KeyStroke.getKeyStroke("LEFT"), "Left");
        keybinds.put(KeyStroke.getKeyStroke("SPACE"), "Space");
		keybinds.put(KeyStroke.getKeyStroke("C"), "AltRotate");

        ActionMap actions = getActionMap();
        actions.put("Up", new UpAction());
        actions.put("Right", new MoveRightAction());
        actions.put("Left", new MoveLeftAction());
        actions.put("Space", new SpaceAction());
        actions.put("Down", new DownAction());
		actions.put("AltRotate", new AltRotateAction());
    }

    private class AltRotateAction extends AbstractAction {
	@Override public void actionPerformed(final ActionEvent e) {
	    if (gameBoard.getFalling() != null) {
		gameBoard.rotate();
	    }
	}
    }

    private class SpaceAction extends AbstractAction {
        @Override public void actionPerformed(final ActionEvent e) {
            if (gameBoard.getCollisionHandler().equals("phase")) {
                if (gameBoard.getFalling() != null) gameBoard.addFalling();
            } else {
                while (gameBoard.getFalling() != null) {
                    gameBoard.fall();
                }
            }
        }
    }

    private class DownAction extends AbstractAction {
        @Override public void actionPerformed(final ActionEvent e) {
            if (gameBoard.getFalling() != null) {
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

    private class UpAction extends AbstractAction {
        @Override public void actionPerformed(final ActionEvent e) {
            if (gameBoard.getCollisionHandler().equals("phase")) {
                gameBoard.moveFallingUp();
            } else {
                if (gameBoard.getFalling() != null) {
                    gameBoard.rotate();
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D tile = (Graphics2D) g;
        final Graphics2D sfx = (Graphics2D) g.create();

        // Setting and drawing the background/line color.
        tile.setColor(bgCol);
        tile.fillRect(0, BAR_HEIGHT, gameBoard.getWidth() * SQUARE_SIZE + PADDING,
                gameBoard.getHeight() * SQUARE_SIZE + PADDING);

        for (int y = 0; y < gameBoard.getHeight(); y++) {
            for (int x = 0; x < gameBoard.getWidth(); x++) {
                if (drawFallingAt(x, y)) {
                    if (gameBoard.getCollisionHandler().equals("fallthrough")) {
                        tile.setColor(fallthruBG);
                        sfx.setColor(fallthruFG);
                    } else if (gameBoard.getCollisionHandler().equals("phase")) {
                        tile.setColor(phaseBG);
                        sfx.setColor(phaseFG);
                    }
                    else {
                        tile.setColor(
                                COLORMAP.get(gameBoard.getFalling().getSquareType(x - gameBoard.getFallingX(),
                                        y - gameBoard.getFallingY())));
                        sfx.setColor(tile.getColor());
                    }
                } else {
                    tile.setColor(COLORMAP.get(gameBoard.getSquareType(x, y)));
                    sfx.setColor(tile.getColor());
                }

                tile.fillRect(x * SQUARE_SIZE + PADDING, y * SQUARE_SIZE + PADDING + BAR_HEIGHT,
                        SQUARE_SIZE - PADDING, SQUARE_SIZE - PADDING);

                sfx.fillRect(x * SQUARE_SIZE + PADDING + SFX_SIZE, y * SQUARE_SIZE + PADDING + SFX_SIZE + BAR_HEIGHT,
                        SQUARE_SIZE - PADDING - SFX_SIZE * 2, SQUARE_SIZE - PADDING - SFX_SIZE * 2);


            }
        }

        tile.setColor(barColor);
        tile.fillRect(0, 0, gameBoard.getWidth() * SQUARE_SIZE + PADDING, BAR_HEIGHT);

        tile.setColor(fontColor);
        tile.setFont(this.dosFont);
        tile.drawString("Score: " + Integer.toString(gameBoard.score), 5, FONT_SIZE - (SQUARE_SIZE - FONT_SIZE) / 2);
        if (gameBoard.getCollisionHandler().equals("fallthrough")) {
            tile.setColor(sfxFontCol);
            tile.drawString("[FALLTHROUGH]", gameBoard.getWidth() * SQUARE_SIZE - (8 * FONT_SIZE), FONT_SIZE - (SQUARE_SIZE - FONT_SIZE) / 2);
        } else if (gameBoard.getCollisionHandler().equals("phase")) {
            tile.setColor(sfxFontCol);
	    tile.drawString("[PHASE]", gameBoard.getWidth() * SQUARE_SIZE - (4 * FONT_SIZE), FONT_SIZE - (SQUARE_SIZE - FONT_SIZE) / 2);
        }

	    // For eventually implementing a pause screen. Can't bear to remove it.
//	    if (gameBoard.isPaused) {
//		    int centerX = ((gameBoard.getWidth() * SQUARE_SIZE) / 2) - (("PAUSE".length() * PAUSE_FONT_SIZE) / 2) + PAUSE_FONT_SIZE;
//		    int centerY = gameBoard.getHeight() * SQUARE_SIZE / 2;
//		    sfx.setColor(pauseBG);
//			tile.setColor(fontColor);
//		    tile.setFont(pauseFont);
//		    sfx.fillRect(centerX - PADDING, centerY - SQUARE_SIZE - 16, "PAUSE".length() * SQUARE_SIZE + PADDING, PAUSE_FONT_SIZE + PADDING);
//		    tile.drawString("PAUSE", centerX, centerY);
//	    }
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
                gameBoard.getHeight() * SQUARE_SIZE + PADDING + BAR_HEIGHT);
    }
}
