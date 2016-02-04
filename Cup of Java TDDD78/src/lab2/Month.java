package lab2;

public class Month {
    private String name;
    private int number;
    private int days;

    public Month(String name, int number, int days) {
        this.name = name;
        this.number = number;
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public int getDays() {
        return days;
    }

    public int getNumber() {
        return number;
    }

    static int getMonthNumber(String name) {
        switch (name) {
            case "January":
                return 1;
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "October":
                return 10;
            case "November":
                return 11;
            case "December":
                return 12;
            default: return -1;
        }
    }

    static int getMonthDays(String name) {
        switch (name) {
            case "January":
            case "March":
            case "May":
            case "July":
            case "August":
            case "October":
            case "December":
                return 31;
	    case "April":
	    case "June":
	    case "September":
	    case "November":
		return 30;
	    case "February":
		return 28;
	    default:
		return -1;
        }
    }
}
