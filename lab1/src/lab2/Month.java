package lab2;

public class Month {
    private String name;
    private Integer number;
    private Integer days;

    public Month(String name, Integer number, Integer days) {
        this.name = name;
        this.number = number;
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public Integer getDays() {
        return days;
    }

    public Integer getNumber() {
        return number;
    }

    static Integer getMonthNumber(String name) {
        switch (name) {
            case "January":
                return 1;
                break;
            case "February":
                return 2;
                break;
            case "March":
                return 3;
                break;
            case "April":
                return 4;
                break;
            case "May":
                return 5;
                break;
            case "June":
                return 6;
                break;
            case "July":
                return 7;
                break;
            case "Augusti":
                return 8;
                break;
            case "September":
                return 9;
                break;
            case "October":
                return 10;
                break;
            case "November":
                return 11;
                break;
            case "December":
                return 12;
                break;
            default: return -1;
        }
    }

    static Integer getMonthDays(String name) {
        switch (name) {
            case "January":
            case "March":
            case "May":
            case "July":
            case "Augusti":
            case "October":
            case "December":
                return 31;
        }
    }
}
