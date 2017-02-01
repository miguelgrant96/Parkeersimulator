package Controllers;


public class TimeController extends AbstractController {

    //Starting Day 1 at: 06:30
    private int day = 1;
    private int dayOfYear = 1;
    private int hour = 06;
    private int minute = 30;

    public TimeController() {

    }

    public void advanceTime() {
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
            dayOfYear++;
        }
        while (day > 7) {
            day -= 7;
        }

    }

    public int getDay() {
        return this.day;
    }
    
    public int getHour() {
        return hour;
    }

    public int getDayOfYear()
    {
        return this.dayOfYear;
    }

    public int getHour()
    {
        return hour;
    }

    public int getMinute()
    {
        return minute;
    }

    //Setting the time in a HH:MM format
    public String getTime() {
        String time;
        if (hour < 10 && minute < 10) {
            time = "0" + hour + ":0" + minute;
        } else if (hour < 10) {
            time = "0" + hour + ":" + minute;
        } else if (minute < 10) {
            time = hour + ":0" + minute;
        } else {
            time = hour + ":" + minute;
        }
        return time;
    }
}
