package tetris;

public interface CollisionHandler {
	public boolean hasCollision(Board gameBoard);

	public String getDescription();
}
