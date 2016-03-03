package tetris;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Board
{
	// How many rotations it takes to activate the FALLTHROUGH power-up.
    private static final int FALLTHRU_REQ = 22;
	public static final int PHASE_CHANCE = 13;

	// Score variables and constants.
	public int score;
	private int rowsRemoved;
	private static final int SCORE_1_ROW = 100;
    private static final int SCORE_2_ROW = 300;
    private static final int SCORE_3_ROW = 500;
    private static final int SCORE_4_ROW = 800;

	// Multidimensional array for storing the squares.
    private final SquareType[][] squares;
	private final int width;
	private final int height;

	// Needed for the activation of the fallthrough power-up.
	private int rotateCount;

	private final List<BoardListener> boardListenerList;
	private final TetrominoMaker tetromaker;
	private CollisionHandler collision;

	private final Random rng;

	private boolean gameOver;


	private Poly falling;
	private int fallingX, fallingY;


	Board(final int width, final int height) {
		this.width = width;
		// I subtract one from height because I want a bar for score
		// and power-up notification.
		this.height = height;
		score = 0;
		rowsRemoved = 0;
		rotateCount = 0;

		rng = new Random();

		gameOver = false;

		falling = null;

		collision = new DefaultCollisionHandler();

		boardListenerList = new ArrayList<>();
		tetromaker = new TetrominoMaker();

		// I add 4 because there will be 2 spaces above and under that are OUTSIDE
		// and 2 to the right and left.
		squares = new SquareType[this.width + 4][this.height + 4];

		createBoard();
	}

	private void createBoard() {
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

    public void resetBoard() {
	score = 0;
	falling = null;
	gameOver = false;

	createBoard();
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

	public boolean isGameRunning() {
		return !gameOver;
	}

	public void setSquareType(int x, int y, SquareType sq) {
		squares[x + 2][y + 2] = sq;
	}

	public void addBoardListener(BoardListener bl) {
		boardListenerList.add(bl);
	}

	public void tick() {
		if (falling == null) {
			falling = tetromaker.getPoly(rng.nextInt(TetrominoMaker.getNumberOfTypes()));
			fallingX = (width - falling.getWidth()) / 2;
			fallingY = 0;
			rotateCount = 0;

			if (rng.nextInt(PHASE_CHANCE) > 1) {
				collision = new Phase();
			}
			else if (collision.getClass() != DefaultCollisionHandler.class) {
				collision = new DefaultCollisionHandler();
			}

			if (collision.hasCollision(this)) {
				falling = null;
				gameOver = true;
			}

		} else {
			// How many rotations to get fallthrough powerup
			if (rotateCount > FALLTHRU_REQ) {
				collision = new Fallthrough();
			}

			removeFullRows();
			calcScore();
			if (!getCollisionHandler().equals("phase"))
				fall();
		}
		notifyListeners();
	}

	public void fall() {
		fallingY++;

		if (collision.hasCollision(this)) {
			fallingY--;
			if (!collision.getDescription().equals("phase"))
				addFalling();
		}
		notifyListeners();
	}

	public String getCollisionHandler() {
		return collision.getDescription();
	}

	private void removeRow(int row) {

		for (int x = 2; x <= width + 2; x++) {
			squares[x][row] = SquareType.EMPTY;
		}

		for (int x = 2; x <= width + 2; x++) {
			System.arraycopy(squares[x], 3, squares[x], 4, row - 3);
		}

		notifyListeners();
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

	private void removeFullRows() {
		rowsRemoved = 0;
		while (getFullRow() != -1) {
			removeRow(getFullRow());
			rowsRemoved++;
		}
		//notifyListeners();
	}

	private void calcScore() {
		switch (rowsRemoved) {
			case 1:
				score += SCORE_1_ROW;
				break;
			case 2:
				score += SCORE_2_ROW;
				break;
			case 3:
				score += SCORE_3_ROW;
				break;
			case 4:
				score += SCORE_4_ROW;
				break;
		}
	}

	public void moveFallingRight() {
		fallingX++;

		if (collision.hasCollision(this)) {
			fallingX--;
		}

		notifyListeners();
	}

	public void moveFallingLeft() {
		fallingX--;

		if (fallingX < -2 || collision.hasCollision(this)) {
			fallingX++;
		}
		notifyListeners();
	}

	public void moveFallingUp() {
		fallingY--;

		if (collision.hasCollision(this)) {
			fallingY++;
		}
		notifyListeners();
	}

	public void rotate() {
		Poly previousPoly = falling;
		falling = falling.rotateRight().rotateRight().rotateRight();
		rotateCount++;

		if (collision.hasCollision(this)) {
			falling = previousPoly;
			rotateCount--;
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

		SquareType[] squareTypes = SquareType.values();

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				squares[i][j] = squareTypes[rng.nextInt(TetrominoMaker.getNumberOfTypes())];
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
