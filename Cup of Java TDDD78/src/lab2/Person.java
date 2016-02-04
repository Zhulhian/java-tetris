package lab2;

import java.time.LocalDate;
import java.time.Period;

public class Person {
    private String name;
    private String verb;
    private LocalDate birthDay;
    boolean isDead;

    public Person(String n, LocalDate bDay, boolean dead) {
        name = n;
        birthDay = bDay;
        isDead = dead;
        verb = isDead ? " would be " : " is ";
    }

    public int getAge() {
        return Period.between(birthDay, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return name + verb + getAge() + " years old today.";
    }

    public static void main(String[] args) {
        Person dali = new Person("Salvador Dali", LocalDate.of(1904, 5, 11), true);
        System.out.println(dali);
        System.out.println(dali.name);
        System.out.println(dali.birthDay);
        System.out.println();

        Person dan = new Person("Daniel Khodarahmi", LocalDate.of(1996, 11, 6), false);
        System.out.println(dan);
        System.out.println(dan.name);
        System.out.println(dan.birthDay);
        System.out.println();
    }
}