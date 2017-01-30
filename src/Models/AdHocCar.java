package Models;

import java.util.Random;
import java.awt.*;

public class AdHocCar extends Car {

    private static final Color COLOR=Color.red;
    private int stayMinutes;

    public AdHocCar() {
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    public Color getColor(){
        return COLOR;
    }
    /*
    Vraag de tijd op per wagen om zo te kunnen berekenen
    Hoeveel er betaald moet worden in totaal
    @return stayMinutes
     */
    public int parkingTime(){
        return stayMinutes;
    }
}