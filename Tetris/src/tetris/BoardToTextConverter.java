package tetris;

public class BoardToTextConverter {

    enum SquareChar {
        EMPTY('\u22C5'), I('\u2503'), O('\u25FC'), T('\u2533'),
        S('\u2440'), Z('\u31C5'), J('\u251B'), L('\u2517');

        public final char squareChar;

        SquareChar(char squareChar) {
            this.squareChar = squareChar;
        }

        static public char getCharFromSquare(SquareType square) {
            switch (square) {
                case EMPTY:
                    return EMPTY.squareChar;
                case I:
                    return I.squareChar;
                case O:
                    return O.squareChar;
                case T:
                    return T.squareChar;
                case S:
                    return S.squareChar;
                case Z:
                    return Z.squareChar;
                case J:
                    return J.squareChar;
                case L:
                    return L.squareChar;
                default: return '!';
            }
        }
    }

    public static String convertToText(Board gameBoard) {

        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < gameBoard.getHeight(); i++) {
            for (int j = 0; j < gameBoard.getWidth(); j++) {

                SquareType currSquare = gameBoard.getSquareType(j, i);

                buf.append(" ");
                if (gameBoard.getFalling() != null &&
                        gameBoard.getFallingX() == j &&
                        gameBoard.getFallingY() == i) {
                    buf.append(SquareChar.getCharFromSquare(gameBoard.getFalling().getSquareType(j, i)));
                } else {
                    buf.append(SquareChar.getCharFromSquare(currSquare));
                }
                /*switch (gameBoard.getSquareType(j, i)) {
                    case EMPTY:
                        if (gameBoard.getFalling() != null &&
                                gameBoard.getFallingX() == j &&
                                gameBoard.getFallingY() == i) {
                            buf.append(gameBoard.getFalling().getSquareType(j, i));
                        }


                        buf.append(SquareChar.EMPTY.squareChar);

                        break;
                    case I:
                        buf.append(SquareChar.I.squareChar);
                        break;
                    case O:
                        buf.append(SquareChar.O.squareChar);
                        break;
                    case T:
                        buf.append(SquareChar.T.squareChar);
                        break;
                    case S:
                        buf.append(SquareChar.S.squareChar);
                        break;
                    case Z:
                        buf.append(SquareChar.Z.squareChar);
                        break;
                    case J:
                        buf.append(SquareChar.J.squareChar);
                        break;
                    case L:
                        buf.append(SquareChar.L.squareChar);
                        break;
                }*/
            }
            buf.append('\n');
        }

        return buf.toString();
    }
}
