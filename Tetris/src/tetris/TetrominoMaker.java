package tetris;

class TetrominoMaker {
    public int getNumberOfTypes() {
        return SquareType.values().length;
    }

    public Poly getPoly(int n) {
        int polyWidth;
        int polyHeight;
        switch (SquareType.values()[n]) {
            case I:
                polyWidth = 4;
                polyHeight = 4;
                break;
            case O:
                polyWidth = 4;
                polyHeight = 3;
                break;
            case L:
            case J:
            case S:
            case Z:
            case T:
                polyWidth = 3;
                polyHeight = 3;
                break;
            default:
                polyWidth = 0;
                polyHeight = 0;

                throw new IllegalArgumentException("Invalid index: " + n);
        }
        SquareType[][] blocks = new SquareType[polyWidth][polyHeight];

        return new Poly(blocks);
    }
}
