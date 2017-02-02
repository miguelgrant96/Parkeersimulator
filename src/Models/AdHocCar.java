package Models;

import Controllers.BetaalAutomaatController;

import java.util.Random;
import java.awt.*;

public class AdHocCar extends Car {

	private static final Color COLOR=Color.red;
	private int stayMinutes;
	private BetaalAutomaatController betaalAutomaatController;

    public AdHocCar() {
        generateParkingTime();
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    public Color getColor(){
        return COLOR;
    }

    private void generateParkingTime(){
        Random random = new Random();
        stayMinutes =  Math.round(15 + random.nextFloat() * 3 * 60);
    }

    /**
    Vraag de tijd op per wagen om zo te kunnen berekenen
    Hoeveel er betaald moet worden in totaal
    @return stayMinutes
     */
    public int getParkingTime(){
        return stayMinutes;
    }

    public boolean getIsPaying(){
        return super.getIsPaying();
    }
}