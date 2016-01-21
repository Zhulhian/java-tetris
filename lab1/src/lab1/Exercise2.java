package lab1;

import javax.swing.*;

public class Exercise2
{
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

	    String inputMin = JOptionPane.showInputDialog("Enter min value");
	    String inputMax = JOptionPane.showInputDialog("Enter max value");

	    min = Integer.parseInt(inputMin);
	    max = Integer.parseInt(inputMax);

	    String loopType = JOptionPane.showInputDialog("What kind of loop do you want? (For or While)");

	    switch (loopType) {
		case "For":
		    System.out.println(sumFor(min, max));
		    break;
		case "While":
		    System.out.println(sumWhile(min, max));
		    break;
		default:
		    System.out.println("Invalid input.");
	    }




	} catch (NumberFormatException input_min) {
	    JOptionPane.showMessageDialog(null, "Input must be Integer");
	}
    }
}
