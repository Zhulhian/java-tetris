package lab2;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Queue {
    private List<Person> elements = new ArrayList<Person>();

    public int size() {return elements.size();}

    public boolean isEmpty() {return elements.isEmpty();}

    public void clear() {elements.clear();}

    public boolean contains(final Object o) {return elements.contains(o);}

    public void enqueue(Person person) {
	elements.add(elements.size(), person);
    }

    public Person dequeue() {
	Person last = elements.get(0);
	elements.remove(0);
	return last;
    }

    public static void main(String[] args) {
	Queue queue = new Queue();
	Person dali = new Person("Salvador Dali", LocalDate.of(1904, 5, 11), true);
	Person kasia = new Person("Katarzyna Ornowska", LocalDate.of(1996, 10, 21), false);
	Person asimov = new Person("Isaac Asimov", LocalDate.of(1920, 1, 2), true);
	Person mcCarthy = new Person("John McCarthy", LocalDate.of(1927, 9, 4), true);
	Person stroustrup = new Person("Bjarne Stroustrup", LocalDate.of(1950, 12, 30), false);

	queue.enqueue(dali);
	queue.enqueue(kasia);
	queue.enqueue(asimov);
	queue.enqueue(mcCarthy);
    	queue.enqueue(stroustrup);

	while (!queue.isEmpty()) {
	    System.out.println(queue.dequeue().toString());
	}

    }
}
