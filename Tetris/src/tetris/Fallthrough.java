package tetris;

public class Fallthrough implements CollisionHandler {

	@Override
	public boolean hasCollision(Board gameBoard) {
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
				if (gameBoard.getSquareType(gameBoard.getFallingX() + x, gameBoard.getFallingY() + y)
						!= SquareType.EMPTY && gameBoard.getFalling().getSquareType(x, y) != SquareType.EMPTY) {
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
