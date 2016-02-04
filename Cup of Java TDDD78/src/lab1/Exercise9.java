package lab1;

import javax.swing.*;

public class Exercise9 {
    public static void main(String[] args) {
        String input =
                JOptionPane.showInputDialog("Please enter the value you want to find the root of");

        double value = Double.parseDouble(input);

        System.out.println("Roten ur " + input + " är " + findRoot(value));
    }

    private static double findRoot(double number) {
        double x = number;

        for (int i = 0; i <= 10; i++) {
            x = x - (x * x - number) / (2 * x);
        }

        return x;
    }
}
