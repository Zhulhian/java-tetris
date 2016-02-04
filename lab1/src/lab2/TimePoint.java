package lab2;

public class TimePoint {
    private String time;
    private int hour;
    private int minute;

    public int getHour() {
	return hour;
    }

    public int getMinute() {
	return minute;
    }

    public TimePoint(final String time) {
	this.time 	= time;
	String[] parts 	= time.split(":");
	this.hour 	= Integer.parseInt(parts[0]);
	this.minute 	= Integer.parseInt(parts[1]);
    }

    @Override public String toString() {
	return time;
    }

    int compareTo(TimePoint other) {
	if ((other.getHour() < this.hour)
	    || ((other.getHour() == this.hour) && (other.getMinute() < this.minute))) {
	    return 1;
	}
	else if ((other.getHour() > this.hour)
		    || ((other.getHour() == this.hour) && (other.getMinute() > this.minute))) {
	    return -1;
	}
	else //if ((other.getHour() == this.hour) && (other.getMinute() == this.minute)) {}
	    return 0;

    }
    public static void main(String[] args) {
	TimePoint time = new TimePoint("21:55");
	TimePoint other = new TimePoint("06:11");
	TimePoint same = new TimePoint("06:11");

	System.out.println("06:11 compared to 06:11 = " + other.compareTo(same));
	System.out.println("06:11 compared to 21:55 = " + other.compareTo(time));
	System.out.println("21:55 compared to 06:11 = " + time.compareTo(other));
    }
}
