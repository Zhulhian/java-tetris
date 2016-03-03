package tetris;

interface CollisionHandler {
	boolean hasCollision(Board gameBoard);

	String getDescription();
}
