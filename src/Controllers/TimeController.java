package Controllers;

/**
 * Created by Arjen on 23-1-2017.
 */
public class TimeController {

    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    public void advanceTime(){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }

    }

    public int GetDay()
    {
        return this.day;
    }

    //return de tijd in hh:mm format
    public String getTime(){
        return hour + ":" + minute;
    }
}
