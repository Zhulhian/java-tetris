package tetris;

public final class BoardToTextConverter {

    private BoardToTextConverter() {}

    enum SquareChar {
        EMPTY('\u22C5'), I('\u2503'), O('\u25FC'), T('\u2533'),
        S('\u2440'), Z('\u31C5'), J('\u251B'), L('\u2517'), OUTSIDE('X');

        public final char squareChar;

        SquareChar(char squareChar) {
            this.squareChar = squareChar;
        }

// --Commented out by Inspection START (04/03/16 15:12):
//        static public char getCharFromSquare(SquareType square) {
//            switch (square) {
//                case EMPTY:
//                    return EMPTY.squareChar;
//                case OUTSIDE:
//                    return OUTSIDE.squareChar;
//                case I:
//                    return I.squareChar;
//                case O:
//                    return O.squareChar;
//                case T:
//                    return T.squareChar;
//                case S:
//                    return S.squareChar;
//                case Z:
//                    return Z.squareChar;
//                case J:
//                    return J.squareChar;
//                case L:
//                    return L.squareChar;
//                default: return '!';
//            }
//        }
// --Commented out by Inspection STOP (04/03/16 15:12)
    }

// --Commented out by Inspection START (04/03/16 15:11):
//    public static String convertToText(Board gameBoard) {
//
//        StringBuilder buf = new StringBuilder();
//
//        for (int i = 0; i < gameBoard.getHeight(); i++) {
//            for (int j = 0; j < gameBoard.getWidth(); j++) {
//
//                SquareType currSquare = gameBoard.getSquareType(j, i);
//
//                buf.append(" ");
//                if (gameBoard.getFalling() != null &&
//                        gameBoard.getFallingX() == j &&
//                        gameBoard.getFallingY() == i) {
//                    buf.append(SquareChar.getCharFromSquare(gameBoard.getFalling().getSquareType(j, i)));
//                } else {
//                    buf.append(SquareChar.getCharFromSquare(currSquare));
//                }
//            }
//            buf.append('\n');
//        }
//
//        return buf.toString();
//    }
// --Commented out by Inspection STOP (04/03/16 15:11)
}
