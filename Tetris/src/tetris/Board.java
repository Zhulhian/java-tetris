package tetris;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates a game board which holds functions and variables relating
 * to game logic
 * */
class Board
{
    // How many rotations it takes to activate the FALLTHROUGH power-up.
	private static final int FALLTHRU_REQ = 22;
	public static final int PHASE_CHANCE = 13;
    public static final int NO_ROW_FOUND = -99;

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
	public boolean isPaused;


	private Poly falling;
	private int fallingX, fallingY;


	Board(final int width, final int height) {
		this.width = width;
		this.height = height;

		score = 0;
		rowsRemoved = 0;
		rotateCount = 0;

		rng = new Random();

		gameOver = false;
		isPaused = false;

		falling = null;

		collision = new DefaultCollisionHandler();

		boardListenerList = new ArrayList<>();
		tetromaker = new TetrominoMaker();

		// I add 4 because there will be 2 spaces above and under that are OUTSIDE
		// and 2 to the right and left that are OUTSIDE, for collision handling.
		squares = new SquareType[this.width + 4][this.height + 4];

		createBoard();
	}

	private void createBoard() {
		// I fill the entire board with OUTSIDE squares first.
		for (int y = 0; y < this.height + 4; y++) {
			for (int x = 0; x < this.width + 4; x++) {
				squares[x][y] = SquareType.OUTSIDE;
			}
		}

		// Then I fill the board with height x width squares, such that
		// it is surrounded with OUTSIDE squares.
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
		// I add 2 to x and y because of the collision border.
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

	public void togglePause() {
		isPaused = !isPaused;
		// I notify the listeners because I want the pause-screen to display
		// as soon as you press pause [P].
		notifyListeners();
	}

	public void tick() {
		// If the game is paused, don't tick.
		if (!isPaused) {
			if (falling == null) {
				// Create a new falling block if none exist.
				falling = tetromaker.getPoly(rng.nextInt(TetrominoMaker.getNumberOfTypes()));
				// Set it's x position to the middle of the screen
				fallingX = (width - falling.getWidth()) / 2;
				fallingY = 0;
				// Reset the rotate count needed for the fallthrough power-up every time
				// a new block is made.
				rotateCount = 0;

				// See if the gods of chaos wants it to be a phase type.
				if (rng.nextInt(PHASE_CHANCE) < 1) {
					collision = new Phase();
				// If not, we create a normal one.
				} else if (collision.getClass() != DefaultCollisionHandler.class) {
					collision = new DefaultCollisionHandler();
				}

				// If there is a collision when creating the block, it means the
				// game is over.
				if (collision.hasCollision(this)) {
					falling = null;
					gameOver = true;
				}

			} else {
				// Check if the required amount of rotations have been made
				// to make the fallthrough power-up activate.
				if (rotateCount > FALLTHRU_REQ) {
					collision = new Fallthrough();
				}

				// Remove any existing full rows.
				removeFullRows();
				// Calculate the score by checking how many rows were removed.
				calcScore();
				// If we don't have the phase power-up, make the falling block fall.
				if (!getCollisionHandler().equals("phase"))
					fall();
			}
		}
		notifyListeners();
	}

	public void fall() {
		fallingY++;

		if (collision.hasCollision(this)) {
			fallingY--;
			// If there's a collision while falling, add the block to the board.
			addFalling();
		}
		notifyListeners();
	}

	// Method for getting the current collision handler i.e. power-up. Mostly
	// used for rendering in the tetris component but also some game logic.
	public String getCollisionHandler() {
		return collision.getDescription();
	}

	private void removeRow(int row) {

		// Remove the row specified entirely
		for (int x = 2; x <= width + 2; x++) {
			squares[x][row] = SquareType.EMPTY;
		}

		// Copy the rows above down a step.
		for (int x = 2; x <= width + 2; x++) {
			// arraycopy is a JNI (Java Native Interface) and as such very fast.
			// I could use a normal for loop as well, for readability's sake.
			System.arraycopy(squares[x], 3, squares[x], 4, row - 3);
		}
	}

	// Checks if a row is full by iterating through it and checking if any of the
	// squares are of SquareType.EMPTY.
	private boolean rowIsFull(int row) {
		for (int i = 2; i < width + 2; i++) {
			if (squares[i][row] == SquareType.EMPTY) {
				return false;
			}
		}
		return true;
	}

	// Iterates through the board row for row and checks if any are full
	// If any is, it returns that row. Worth noting that it searches from
	// the bottom and up. If no rows are full, it returns -99.
	private int getFullRow() {
		for (int row = 2; row < height + 2; row++) {
			if (rowIsFull(row)) {
				return row;
			}
		}
		return NO_ROW_FOUND;
	}

	// Sets rowsRemoved to zero, then removes all full rows and then
	// increments it by one for every removed row.
	private void removeFullRows() {
		rowsRemoved = 0;
		while (getFullRow() != NO_ROW_FOUND) {
			removeRow(getFullRow());
			rowsRemoved++;
		}
		notifyListeners();
	}

	// Checks the amount of rows removed and adds to score
	// accordingly.
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



	// - - - - -  Movement functions  - - - - - //

	public void moveFallingRight() {
		fallingX++;

		if (collision.hasCollision(this)) {
			fallingX--;
		}

		notifyListeners();
	}

	// The task states we have to add 2 OUTSIDE blocks for collision
	// handling, but when we try to move the I block to the left in
	// certain positions it crashes the game because the fallingX is
	// the top left corner, which becomes negative when at the very edge.
	// I add a simple check here to prevent that crash.
	public void moveFallingLeft() {
		fallingX--;

		if (fallingX < -2 || collision.hasCollision(this)) {
			fallingX++;
		}
		notifyListeners();

	}

	// This one is exclusively used for the phase block.
	public void moveFallingUp() {
		fallingY--;

		if (collision.hasCollision(this)) {
			fallingY++;
		}
		notifyListeners();
	}

	public void rotate() {
		Poly previousPoly = falling;
		// It seems more intuitive to rotate clockwise, so I just rotate
		// the block three times.
		falling = falling.rotateLeft().rotateLeft().rotateLeft();
		rotateCount++;

		if (collision.hasCollision(this)) {
			falling = previousPoly;
			rotateCount--;
		}

		notifyListeners();
	}



	public void addFalling() {
		// Adds the falling block to the board.
		for (int y = 0; y < falling.getWidth(); y++) {
			for (int x = 0; x < falling.getHeight(); x++) {
				if (falling.getSquareType(x, y) != SquareType.EMPTY) {
					squares[fallingX + x + 2][fallingY + y + 2] = falling.getSquareType(x, y);
				}
			}
		}
		falling = null;
	}

	// Goes through every board listener and calls their boardChanged function.
	private void notifyListeners() {
		boardListenerList.forEach(BoardListener::boardChanged);
	}

//
//    public void randomizeBoard() {
//
//	SquareType[] squareTypes = SquareType.values();
//
//	for (int i = 0; i < height; i++) {
//	    for (int j = 0; j < width; j++) {
//		squares[i][j] = squareTypes[rng.nextInt(TetrominoMaker.getNumberOfTypes())];
//	    }
//	}
//
//	notifyListeners();
//    }
//

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
