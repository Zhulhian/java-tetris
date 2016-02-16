package tetris;

public class Poly {
    private final SquareType[][] polymino;
    public Poly(SquareType[][] polymino) {
        this.polymino = polymino;
    }

    public SquareType getSquareType(int x, int y) {
        return polymino[x][y];
    }
}
