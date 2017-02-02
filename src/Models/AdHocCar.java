package Models;

import Controllers.PaymentController;

import java.awt.*;

public class AdHocCar extends Car {

	private static final Color COLOR=Color.red;
	private int stayMinutes;
	private PaymentController paymentController;

    public AdHocCar() {
        this.stayMinutes = super.generateParkingTime();
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    public Color getColor(){
        return COLOR;
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