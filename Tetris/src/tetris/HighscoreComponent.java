package tetris;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

class HighscoreComponent extends JComponent {
    private final HighscoreList highscoreList;

    // If the fonts don't get iniitialized, it falls back to the default font.
    private Font entryFont;
    private Font titleFont;
    private final static int FONT_SIZE = 32;
    private final Color entryColor;
    private final Color titleColor;

    private final Color bgCol;

    HighscoreComponent(HighscoreList highscoreList) {
        this.highscoreList = highscoreList;

        try {
            this.entryFont = Font.createFont(Font.TRUETYPE_FONT, new File("dosvga.ttf"));
            this.entryFont = this.entryFont.deriveFont(Font.TRUETYPE_FONT, FONT_SIZE);
            titleFont = entryFont.deriveFont(Font.BOLD, FONT_SIZE);
        } catch(FontFormatException|IOException ex) {
            System.out.println("Exception: " + ex);
        }

        this.entryColor = new Color(253, 120, 120);
        this.titleColor = new Color(125, 189, 253);
        this.bgCol = new Color(253, 253, 150);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        final Graphics2D gfx = (Graphics2D) g;

        gfx.setColor(bgCol);
        gfx.fillRect(0, 0, BoardTest.WIDTH * TetrisComponent.SQUARE_SIZE,
                          BoardTest.HEIGHT * TetrisComponent.SQUARE_SIZE);

        gfx.setColor(titleColor);
        gfx.setFont(titleFont);

        gfx.drawString("H I - S C O R E", 3 * FONT_SIZE, FONT_SIZE + 2);

        gfx.setColor(entryColor);
        gfx.setFont(entryFont);

        int y = FONT_SIZE * 3;
        for (Score hs : highscoreList.getHighscore()) {
            gfx.drawString(" * " + hs.name + ":", 2, y);
            gfx.drawString(Integer.toString(hs.score),
                    (BoardTest.WIDTH * TetrisComponent.SQUARE_SIZE) * 3/4, y);
            y += FONT_SIZE + 2;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BoardTest.WIDTH * TetrisComponent.SQUARE_SIZE,
                            BoardTest.HEIGHT * TetrisComponent.SQUARE_SIZE);
    }

}
