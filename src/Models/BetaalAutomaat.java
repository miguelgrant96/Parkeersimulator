package Models;

/**
 * Created by Jop on 24-1-2017.
 */
public class BetaalAutomaat {

    private double kostenPM = 0.05;
    private double kostenTotaal;
    private AdHocCar car;


    public BetaalAutomaat(){
    }

    public int dagOmzet(){
       return car.getTotalCars();
    }

    public int getTimeParked(){
        return car.getParkingTime();
    }

}
