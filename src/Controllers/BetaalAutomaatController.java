package Controllers;

import Models.AdHocCar;

/**
 * Created by Jop on 24-1-2017.
 */
public class BetaalAutomaatController {

    private double kostenPM = 0.05;
    private double dagOmzet;
    private double verwachteDagOmzet;
    private double weekOmzet;
    private AdHocCar car;


    public BetaalAutomaatController(){

    }

    public double getDagOmzet(){

        //Een statement die elke dag hem weer op 0 zet
        if(car.getIsPaying()) {
            dagOmzet = (car.getParkingTime() * kostenPM) + dagOmzet;
        }
        return dagOmzet;
    }

    public double getVerwachteDagOmzet(){
        return verwachteDagOmzet += (car.getParkingTime() * kostenPM);
    }

    public double getWeekOmzet(){
        return car.getTotalCars();
    }

    public int getTimeParked(){
        return car.getParkingTime();
    }

}
