package tetris;

import java.util.ArrayList;
import java.util.Random;

class Board {
    private final SquareType[][] squares;
    private int width;
    private int height;

    private ArrayList<BoardListener> boardListenerList;
    private TetrominoMaker tetromaker;

    private Random rng = new Random();

    private Poly falling = null;
    private int fallingX, fallingY;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;

        squares = new SquareType[height][width];
        boardListenerList = new ArrayList<BoardListener>();
        tetromaker = new TetrominoMaker();

        // Change to width/height maybe?
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                squares[i][j] = SquareType.EMPTY;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public SquareType getSquareType(int x, int y) {
        return squares[y][x];
    }

    public void addBoardListener(BoardListener bl) {
        boardListenerList.add(bl);
    }

    public void removeBoardListener(BoardListener bl) {
        boardListenerList.remove(bl);
    }

    public void tick() {
        if (falling == null) {
            fallingX = (int)(width / 2);
            fallingY = 0;
            falling = tetromaker.getPoly(rng.nextInt(tetromaker.getNumberOfTypes()));
        } else {
            fallingY += 1;
        }
        notifyListeners();
    }

    public void moveFallingRight() {
        fallingX++;
        notifyListeners();
    }

    public void moveFallingLeft() {
        fallingX--;
        notifyListeners();
    }

    private void notifyListeners() {
        boardListenerList.forEach(BoardListener::boardChanged);
    }

    public void randomizeBoard() {
        java.util.Random rng = new java.util.Random();

        SquareType[] squareTypes = SquareType.values();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                squares[i][j] = squareTypes[rng.nextInt(squareTypes.length)];
            }
        }

        notifyListeners();
    }

    public int getFallingY() {
        return fallingY;
    }

    public int getFallingX() {
        return fallingX;
    }

    public Poly getFalling() {
        return falling;
    }
}
