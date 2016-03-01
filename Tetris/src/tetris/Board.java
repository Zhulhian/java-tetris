package tetris;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Board
{
    private SquareType[][] squares;
    private int width;
    private int height;

    public int score;
    private int rowsRemoved;

    private List<BoardListener> boardListenerList;
    private TetrominoMaker tetromaker;

    private Random rng;
    private boolean gameOver;

    private Poly falling;
    private int fallingX, fallingY;

    public Board(final int width, final int height) {
	this.width = width;
	this.height = height;

	score = 0;
	rowsRemoved = 0;

	rng = new Random();
	gameOver = false;

	falling = null;

	boardListenerList = new ArrayList<>();
	tetromaker = new TetrominoMaker();

	// 2 is the outside space for collision handling.
	squares = new SquareType[this.width + 4][this.height + 4];

	createBoard();
    }

    public void createBoard() {
	for (int y = 0; y < this.height + 4; y++) {
	    for (int x = 0; x < this.width + 4; x++) {
		squares[x][y] = SquareType.OUTSIDE;
	    }
	}

	for (int y = 2; y < this.height + 2; y++) {
	    for (int x = 2; x < this.width + 2; x++) {
		squares[x][y] = SquareType.EMPTY;
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
	return squares[x + 2][y + 2];
    }

    public boolean isGameOver() {
	return gameOver;
    }

    public void setSquareType(int x, int y, SquareType sq) {
	squares[x + 2][y + 1] = sq;
    }

    public boolean hasCollision() {
	for (int y = 0; y < falling.getHeight(); y++) {
	    for (int x = 0; x < falling.getWidth(); x++) {
		if ((getSquareType(x + fallingX, y + fallingY) != SquareType.EMPTY) &&
		    (falling.getSquareType(x, y) != SquareType.EMPTY)) {
		    return true;
		}
	    }
	}
	return false;
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
	    fallingY = 1;
	    falling = tetromaker.getPoly(rng.nextInt(TetrominoMaker.getNumberOfTypes()));

	    if (hasCollision()) {
		falling = null;
	    }

	} else {
	    removeFullRows();
	    calcScore();
	    fall();
	}
	notifyListeners();
    }

    public void fall() {
    	fallingY++;

    	if (hasCollision()) {
    	    fallingY--;
    	    addFalling();
    	}
    }

    public void removeRow(int row) {

	for (int x = 2; x <= width + 2; x++) {
	    squares[x][row] = SquareType.EMPTY;
	}

	for (int x = 2; x <= width + 2; x++) {
	    System.arraycopy(squares[x], 2, squares[x], 3, row - 2);
	}
    }

    private boolean rowIsFull(int row) {
	for (int i = 2; i < width + 2; i++) {
	    if (squares[i][row] == SquareType.EMPTY) {
		return false;
	    }
	}
	return true;
    }

    private int getFullRow() {
	for (int row = 2; row < height + 2; row++) {
	    if (rowIsFull(row)) {
		return row;
	    }
	}
	return -1;
    }

    public void printFirstTwoColumn() {
	for (int x = width + 2; x < width + 4; x++) { System.out.println(squares[x][fallingY]); }
    }

    public void removeFullRows() {
	rowsRemoved = 0;
	while (getFullRow() != -1) {
	    removeRow(getFullRow());
	    rowsRemoved++;
	}
    }

    public void calcScore() {
	switch (rowsRemoved) {
	    case 1:
		score += 100;
		break;
	    case 2:
		score += 300;
		break;
	    case 3:
		score += 500;
		break;
	    case 4:
		score += 800;
		break;
	}
    }

    public void moveFallingRight() {
	fallingX++;

	if (hasCollision()) {
	    fallingX--;
	}

	notifyListeners();
    }

    public void moveFallingLeft() {
	fallingX--;

	if (fallingX < -2 || hasCollision()) {
	    fallingX++;
	}
	notifyListeners();
    }

    public void rotate() {
	Poly previousPoly = falling;
	falling = falling.rotateRight().rotateRight().rotateRight();

	if (hasCollision()) {
	    falling = previousPoly;
	}

	notifyListeners();
    }



    public void addFalling() {
	for (int y = 0; y < falling.getWidth(); y++) {
	    for (int x = 0; x < falling.getHeight(); x++) {
		if (falling.getSquareType(x, y) != SquareType.EMPTY) {
		    squares[fallingX + x + 2][fallingY + y + 2] = falling.getSquareType(x, y);
		}
	    }
	}
	falling = null;
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
