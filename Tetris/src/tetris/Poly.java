package tetris;

/**
 * A polymino piece, made up of SquareTypes.
 * */
public class Poly {

    private final SquareType[][] polymino;

    public Poly(SquareType[][] polymino) {
        this.polymino = polymino;
    }

    public SquareType getSquareType(int x, int y) {
        return polymino[x][y];
    }

    public Poly rotateLeft() {
        int size = polymino.length;
        Poly rotatedPoly = new Poly(new SquareType[size][size]);

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                rotatedPoly.polymino[x][size-1-y] = this.polymino[y][x];
            }
        }

        return rotatedPoly;
    }

    // It's not really necessary to have both a getWidth and getHeight
    // function, since they are the same, but it might be useful for
    // future expansions.
    public int getWidth() {
        return polymino[0].length;
    }

    public int getHeight() {
        return polymino.length;
    }
}
