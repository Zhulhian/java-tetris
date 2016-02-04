package lab2;

import java.util.ArrayList;
import java.util.List;

public class Calendar {
    private List<Appointment> appointments;

    public Calendar() {
	appointments = new ArrayList<>();
    }

    public void show() {
	for (Appointment appointment : appointments) {
	    System.out.println(appointment.toString());
	}
    }

    public void book(int year, String month, int day,
		     String start, String end, String subject) {
	if (year < 2016) throw new IllegalArgumentException("Can't book appointments in the past.");
	if (Month.getMonthNumber(month) == -1 ) throw new IllegalArgumentException("Invalid month name. Try capitalizing.");
	if (day > Month.getMonthDays(month)) throw new IllegalArgumentException(month + " does not have that many days.");
	if (start.compareTo(end) == 1) throw new IllegalArgumentException("Start time should come before end time!");

	Month appMonth = new Month(month, Month.getMonthNumber(month), Month.getMonthDays(month));

	Date appDate = new Date(year, day, appMonth);

	TimePoint appStart = new TimePoint(start);
	TimePoint appEnd = new TimePoint(end);

	TimeSpan appSpan = new TimeSpan(appStart, appEnd);

	Appointment app = new Appointment(subject, appDate, appSpan);

	appointments.add(app);
    }

    public static void main(String[] args) {
	Calendar cal = new Calendar();

	cal.book(2016, "November", 6, "00:00", "23:59", "My birthday!");
	cal.book(2016, "October", 21, "00:00", "23:59", "Kasia's birthday!");
	cal.book(2099, "August", 25, "15:00", "15:00", "Release of Half-life 3");
	cal.book(2016, "May", 21, "00:00", "23:59", "Asparagus day!");
	cal.book(2020, "August", 25, "14:37", "14:38", "World War III");

	cal.show();
    }
}
