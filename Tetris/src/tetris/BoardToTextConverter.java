package tetris;

public class BoardToTextConverter {

    enum SquareChar {
        EMPTY('\u22C5'), I('\u2503'), O('\u25FC'), T('\u2533'),
        S('\u2440'), Z('\u31C5'), J('\u251B'), L('\u2517');

        public char squareChar;

        SquareChar(char squareChar) {
            this.squareChar = squareChar;
        }
    }

    public static String convertToText(Board gameBoard) {

        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < gameBoard.getHeight(); i++) {
            for (int j = 0; j < gameBoard.getWidth(); j++) {
                //buf.append(" ");
                switch (gameBoard.getSquareType(j, i)) {
                    case EMPTY:
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
                }
                //buf.append(" ");

            }
            buf.append('\n');
        }

        return buf.toString();
    }
}
