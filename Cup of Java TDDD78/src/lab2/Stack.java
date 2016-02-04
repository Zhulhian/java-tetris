package lab2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Stack
{
    private List<Person> elements = new ArrayList<Person>();

    public int size() {return elements.size();}

    public boolean isEmpty() {return elements.isEmpty();}

    public void clear() {elements.clear();}

    public boolean contains(final Object o) {return elements.contains(o);}

    public void push(Person person) {
	elements.add(0, person);
    }

    public Person pop() {
	Person first = elements.get(0);
	elements.remove(0);
	return first;
    }

    public static void main(String[] args) {
    	Stack stack = new Stack();
    	Person dali = new Person("Salvador Dali", LocalDate.of(1904, 5, 11), true);
    	Person kasia = new Person("Katarzyna Ornowska", LocalDate.of(1996, 10, 21), false);
    	Person asimov = new Person("Isaac Asimov", LocalDate.of(1920, 1, 2), true);
    	Person mcCarthy = new Person("John McCarthy", LocalDate.of(1927, 9, 4), true);
    	Person stroustrup = new Person("Bjarne Stroustrup", LocalDate.of(1950, 12, 30), false);

    	stack.push(dali);
    	stack.push(kasia);
    	stack.push(asimov);
    	stack.push(mcCarthy);
	stack.push(stroustrup);

    	while (!stack.isEmpty()) {
    	    System.out.println(stack.pop().toString());
    	}

    }
}
