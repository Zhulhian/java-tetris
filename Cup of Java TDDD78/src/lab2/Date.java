package lab2;

public class Date {
    int year;
    int day;
    Month month;

    public Date(final int year, final int day, final Month month) {
	this.year = year;
	this.day = day;
	this.month = month;
    }

    public int getYear() {
	return year;
    }

    public int getDay() {
	return day;
    }

    public Month getMonth() {
	return month;
    }

    @Override public String toString() {
	return year + "-" + month.getName() + "-" + day;
    }
}
