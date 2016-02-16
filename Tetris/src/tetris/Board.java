package tetris;

class Board {
    private final SquareType[][] squares;
    private int width;
    private int height;

    private final Poly falling = null;
    private int fallingX, fallingY;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        squares = new SquareType[height][width];

        // Change to width/height maybe?
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                squares[i][j] = SquareType.EMPTY;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public SquareType getSquareType(int x, int y) {
        return squares[y][x];
    }

    public void randomizeBoard() {
        java.util.Random rng = new java.util.Random();

        SquareType[] squareTypes = SquareType.values();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                squares[i][j] = squareTypes[rng.nextInt(squareTypes.length)];
            }
        }

    }

    public int getFallingY() {
        return fallingY;
    }

    public int getFallingX() {
        return fallingX;
    }

    public Poly getFalling() {
        return falling;
    }

    public static void main(String[] args) {
        Board gameBoard = new Board(20, 50);

        System.out.println("Height: " + gameBoard.height);
        System.out.println("Width: " + gameBoard.width);
    }
}
