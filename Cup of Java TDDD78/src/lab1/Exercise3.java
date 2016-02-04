package lab1;

import javax.swing.*;

public class Exercise3 {
    public static void main(String[] args) {

        try {
            String input =
                    JOptionPane.showInputDialog("Please enter a value");

            int tabell;
            tabell = Integer.parseInt(input);

            for (int i = 0; i <= 12; i++) {
                System.out.println(i + " * " + tabell + " = " + i * tabell);
            }

        } catch (NumberFormatException input) {
            JOptionPane.showMessageDialog(null, "Error: must enter Integer.");
        }
    }
}
