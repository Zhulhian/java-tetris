package tetris;

public class Phase implements CollisionHandler {
	@Override
	public boolean hasCollision(Board gameBoard) { ////////////////////////////////////////////////////////////////////

			for (int y = 0; y < gameBoard.getFalling().getHeight(); y++) {
			for (int x = 0; x < gameBoard.getFalling().getHeight(); x++) {
				if (gameBoard.getSquareType(gameBoard.getFallingX() + x, gameBoard.getFallingY() + y)
						== SquareType.OUTSIDE && gameBoard.getFalling().getSquareType(x, y) != SquareType.EMPTY) {
					return true;
				}
			}
		}


		for (int y = 0; y < gameBoard.getFalling().getHeight(); y++) {
			for (int x = 0; x < gameBoard.getFalling().getHeight(); x++) {
				if (gameBoard.getSquareType(gameBoard.getFallingX() + x, gameBoard.getFallingY() + y) != SquareType.EMPTY &&
						gameBoard.getFalling().getSquareType(x, y) != SquareType.EMPTY) {
					gameBoard.addFalling();
				}
			}
		}

		return false;
	}

	@Override
	public String getDescription() {
		return "phase";
	}
}
