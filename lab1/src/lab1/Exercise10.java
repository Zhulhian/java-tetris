package lab1;

/**
 * Created by daniel on 1/25/2016.
 */
public class Exercise10 {
    public static void main(String[] args) {
        int tal = 16777217;
        int big = 2147483647;
        long bigger = big + 1;
        long biggerFirstL = (long)big + 1;
        long biggerSecondL = big + 1L;
        double decimaltal = tal;
        int tillbaka = (int)decimaltal;

        System.out.println("tal = " + tal);
        System.out.println("decimaltal = " + decimaltal);
        System.out.println("tillbaka = " + tillbaka);

        System.out.println("big = \t\t\t\t" + big);
        System.out.println("bigger = \t\t\t" + bigger);
        System.out.println("biggerFirstL = \t\t" + biggerFirstL);
        System.out.println("biggerSecondL = \t" + biggerSecondL);
    }
}
