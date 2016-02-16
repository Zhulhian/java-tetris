package tetris;

public class BoardTest {
    public static void main(String[] args) {
        Board gameBoard = new Board(20, 50);

        for (int i = 0; i < 10; i++) {
            gameBoard.randomizeBoard();
        }

        System.out.println(BoardToTextConverter.convertToText(gameBoard));
    }
}
