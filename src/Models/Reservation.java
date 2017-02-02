package Models;

import java.awt.*;

/**
 * Created by Arjen on 1-2-2017.
 */
public class Reservation extends Car {
    private static final Color COLOR=Color.GREEN;
    private int dayOfArrival;
    private int hourOfArrival;
    private int minuteOfArrival;

    public Reservation(int day, String time)
    {
        int[] times = parseTime(time);

        this.dayOfArrival = day;
        this.hourOfArrival = times[0];
        this.minuteOfArrival = times[1];

        this.setMinutesLeft((((dayOfArrival-1)*24)+hourOfArrival)*60 + minuteOfArrival + 30);
        this.setHasToPay(false);
    }

    private int[] parseTime(String time)
    {
        int[] Itimes = new int[2];
        String[] Stimes = time.split(":");
        Itimes[0] = Integer.parseInt(Stimes[0]);
        Itimes[1] = Integer.parseInt(Stimes[1]);

        return Itimes;
    }

    public int getDayOfArrival()
    {
        return this.dayOfArrival;
    }
    public int getHourOfArrival()
    {
        return this.hourOfArrival;
    }
    public int getMinuteOfArrival()
    {
        return this.minuteOfArrival;
    }

    public Color getColor(){
        return COLOR;
    }
}
