package tetris;

/**
 * Collision handler for the Fallthrough powerup.
 * It deletes everything it touches, except borders.
 * (OUTSIDE squares)
 */
public class Fallthrough implements CollisionHandler {

	@Override
	public boolean hasCollision(Board gameBoard) {

		// Checks if any of the blocks in the tetromino that aren't empty
		// have collided with an OUTSIDE block. Basically, it only returns
		// true if it touches an OUTSIDE block.
		for (int y = 0; y < gameBoard.getFalling().getHeight(); y++) {
			for (int x = 0; x < gameBoard.getFalling().getHeight(); x++) {
				if (gameBoard.getSquareType(gameBoard.getFallingX() + x, gameBoard.getFallingY() + y)
						== SquareType.OUTSIDE && gameBoard.getFalling().getSquareType(x, y) != SquareType.EMPTY) {
					return true;
				}
			}
		}

		// Turns every block that it touches that aren't empty into empty blocks.
		for (int y = 0; y < gameBoard.getFalling().getHeight(); y++) {
			for (int x = 0; x < gameBoard.getFalling().getHeight(); x++) {
				if (gameBoard.getSquareType(gameBoard.getFallingX() + x, gameBoard.getFallingY() + y) != SquareType.EMPTY &&
						gameBoard.getFalling().getSquareType(x, y) != SquareType.EMPTY) {
					gameBoard.setSquareType(gameBoard.getFallingX() + x, gameBoard.getFallingY() + y, SquareType.EMPTY);
				}
			}
		}

		return false;
	}

	@Override
	public String getDescription() {
		return "fallthrough";
	}
}
