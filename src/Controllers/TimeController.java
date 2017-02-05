package Controllers;


public class TimeController extends AbstractController{

    //Starting Day 1 at: 06:30
    private int minute = 30;
    private int hour = 06;
    private int day = 1;
    private int week = 1;
    private int month = 1;
    private int year = 2017;
    private int dayOfYear = 1;

    public TimeController() {
    }

    /**
     * The "Controls" of the main timecontroller
     */
    public void advanceTime() {
        // Advance the time by 30 minutes.
        minute+=30;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 7) {
            day -= 7;
            week++;
        }
        while (week > 4){
            week -= 4;
            month++;
        }
        while (month > 12){
            month -= 12;
            year++;
        }
    }

    /**
     *
     * @return return the value of year
     */
    public int getYear(){
        return year;
    }

    /**
     *
     * @return return the value of month
     */
    public int getMonth() {
        return month;
    }

    /**
     *
     * @return return the value of week
     */
    public int getWeek() {
        return week;
    }

    /**
     *
     * @return return the value of day
     */
    public int getDay() {
        return this.day;
    }

    /**
     *
     * @return return the value of dayOfYear
     */
    public int getDayOfYear(){
        return dayOfYear;
    }

    /**
     *
     * @return return the value of hour
     */
    protected int getHour() {
        return hour;
    }

    /**
     *
     * @return return the value of minute
     */
    protected int getMinute(){
        return minute;
    }


    /**
     * Setting the time in a HH:MM format
     * @return return the value of hour+minute
     */
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

    public void resetTime()
    {
        minute = 30;
        hour = 06;
        day = 1;
        week = 1;
        month = 1;
        year = 2017;
        dayOfYear = 1;
    }
}
