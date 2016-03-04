package tetris;

public class Phase implements CollisionHandler {
	@Override
	public boolean hasCollision(Board gameBoard) {

		// Pretty much the same collision handling as the default one.
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
		return "phase";
	}
}