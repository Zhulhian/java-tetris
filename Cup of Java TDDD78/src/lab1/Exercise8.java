package lab1;

import javax.swing.*;

public class Exercise8
{
    public static void main(String[] args) {
        while (true) {
            if (askUser("Quit?") && askUser("Really?")) {
                break;
            }
//            if (askUser("Quit?") & askUser("Really?")) {
//                break;
//            }
        }
    }
    private static boolean askUser(String query) {
        return JOptionPane.showConfirmDialog(null, query, "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
