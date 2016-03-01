package tetris;

class TetrominoMaker {
    public static int getNumberOfTypes() {
        return SquareType.values().length - 2 ;
    }

    public Poly getPoly(int n) {
        switch (SquareType.values()[n]) {
            case I:
                return createIPiece();
            case O:
                return createOPiece();
            case L:
                return createLPiece();
            case J:
                return createJPiece();
            case S:
                return createSPiece();
            case Z:
                return createZPiece();
            case T:
                return createTPiece();
            default:
                return null;
        }
    }

    private Poly createIPiece() {

        SquareType[][] squares = new SquareType[4][4];
        fillWithEmpty(squares);

        squares[1][0] = SquareType.I;
        squares[1][1] = SquareType.I;
        squares[1][2] = SquareType.I;
        squares[1][3] = SquareType.I;

        return new Poly(squares);

    }

    private Poly createSPiece() {

        SquareType[][] squares = new SquareType[3][3];
        fillWithEmpty(squares);

        squares[1][0] = SquareType.S;
        squares[2][0] = SquareType.S;
        squares[0][1] = SquareType.S;
        squares[1][1] = SquareType.S;

        return new Poly(squares);
    }

    private Poly createTPiece() {

        SquareType[][] squares = new SquareType[3][3];
        fillWithEmpty(squares);

        squares[1][0] = SquareType.T;
        squares[0][1] = SquareType.T;
        squares[1][1] = SquareType.T;
        squares[2][1] = SquareType.T;

        return new Poly(squares);
    }

    private Poly createZPiece() {

        SquareType[][] squares = new SquareType[3][3];
        fillWithEmpty(squares);

        squares[0][0] = SquareType.Z;
        squares[1][0] = SquareType.Z;
        squares[1][1] = SquareType.Z;
        squares[2][1] = SquareType.Z;

        return new Poly(squares);
    }

    private Poly createJPiece() {

        SquareType[][] squares = new SquareType[3][3];
        fillWithEmpty(squares);

        squares[0][0] = SquareType.J;
        squares[0][1] = SquareType.J;
        squares[1][1] = SquareType.J;
        squares[2][1] = SquareType.J;

        return new Poly(squares);
    }

    private Poly createLPiece() {

        SquareType[][] squares = new SquareType[3][3];
        fillWithEmpty(squares);

        squares[2][0] = SquareType.L;
        squares[0][1] = SquareType.L;
        squares[1][1] = SquareType.L;
        squares[2][1] = SquareType.L;

        return new Poly(squares);
    }

    private Poly createOPiece() {

        SquareType[][] squares = new SquareType[2][2];
        fillWithEmpty(squares);

        squares[0][0] = SquareType.O;
        squares[0][1] = SquareType.O;
        squares[1][0] = SquareType.O;
        squares[1][1] = SquareType.O;

        return new Poly(squares);
    }

    public void fillWithEmpty(SquareType[][] blocks) {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                blocks[j][i] = SquareType.EMPTY;
            }
        }
    }

}
