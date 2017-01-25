package Models;

/**
 * Created by Jop on 24-1-2017.
 */
public class BetaalAutomaat {

    private double kostenPM = 0.05;
    private double kostenTotaal;
    private AdHocCar car;

    public BetaalAutomaat(){
        kostenTotaal = (getTimeParked() * kostenPM) + kostenTotaal;
    }

    private int getTimeParked(){
        return car.parkingTime();
    }

}
