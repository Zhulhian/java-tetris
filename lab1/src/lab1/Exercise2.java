package lab1;

import javax.swing.*;

public class Exercise2 {
    static int sumFor(int min, int max) {
        int sum = 0;
        for (; min <= max; min++) {
            sum += min;
        }
        return sum;
    }

    static int sumWhile(int min, int max) {
        int sum = 0;
        while (min <= max) {
            sum += min;
            min++;
        }

        return sum;
    }

    public static void main(String[] args) {

        try {
            int min;
            int max;

            String input_min = JOptionPane.showInputDialog("Enter min value");
            String input_max = JOptionPane.showInputDialog("Enter max value");

            min = Integer.parseInt(input_min);
            max = Integer.parseInt(input_max);

            System.out.print("For: ");
            System.out.println(sumFor(min, max));

            System.out.print("While: ");
            System.out.println(sumWhile(min, max));
        } catch (NumberFormatException input_min) {
            JOptionPane.showMessageDialog(null, "Input must be Integer");
        }
    }
}
