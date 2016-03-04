package tetris;

/**
 * Interface for the collision handlers/power-ups.
 */
interface CollisionHandler {
	boolean hasCollision(Board gameBoard);

	String getDescription();
}
