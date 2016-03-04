package tetris;

/**
 * The collision handler for the normal falling block.
 */
public class DefaultCollisionHandler implements CollisionHandler {
	@Override
	public boolean hasCollision(Board gameBoard) {

		// Checks if any of the blocks in the tetromino have collided with
		// any other block that isn't empty. That means all different tetro-
		// mino blocks and outside blocks.
		for (int y = 0; y < gameBoard.getFalling().getHeight(); y++) {
			for (int x = 0; x < gameBoard.getFalling().getWidth(); x++) {
				if ((gameBoard.getSquareType(x + gameBoard.getFallingX(),
						y + gameBoard.getFallingY()) != SquareType.EMPTY) &&
						(gameBoard.getFalling().getSquareType(x, y) != SquareType.EMPTY)) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public String getDescription() {
		return "normal";
	}
}
