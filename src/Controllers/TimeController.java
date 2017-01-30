package Controllers;

import Views.TimeView;

/**
 * Created by Arjen on 23-1-2017.
 */
public class TimeController {

    private int day = 0;
    private int hour = 23;
    private int minute = 29;

    public TimeController(){
    }

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
    public String getTime() {
        String time;
        if (hour < 10 && minute < 10) {
            time = "0"+hour+":0"+minute;
        }else if(hour < 10){
            time = "0"+hour+":"+minute;
        }else if(minute < 10){
            time = hour+":0"+minute;
        }else{
            time = hour+":"+minute;
        }
        return time;
    }
}