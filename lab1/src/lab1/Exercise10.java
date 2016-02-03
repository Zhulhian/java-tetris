package lab1;

public class Exercise10 {
    public static void main(String[] args) {
        int num = 16777217;
        int big = 2147483647;
        long bigger = big + 1;
        long biggerAlternative = (long)big + 1;
        long biggerSecondAlternative = big + 1L;
        double decimalNum = num;
        int backAgain = (int)decimalNum;

        System.out.println("num = " + num);
        System.out.println("decimalNum = " + decimalNum);
        System.out.println("backAgain = " + backAgain);

        System.out.println("big = \t\t\t\t" + big);
        System.out.println("bigger = \t\t\t" + bigger);
        System.out.println("biggerAlternative = \t\t" + biggerAlternative);
        System.out.println("biggerSecondAlternative = \t" + biggerSecondAlternative);
    }
}
