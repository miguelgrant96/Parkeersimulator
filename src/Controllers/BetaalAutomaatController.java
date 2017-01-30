package Controllers;

import Models.*;

import java.awt.*;
import java.sql.Time;

/**
 * Created by Jop on 24-1-2017.
 */
public class BetaalAutomaatController {

    private Car carPaying;
    private CarController carController;

    private double kostenPM = 0.05;
    private double dagOmzet = 0;


    public BetaalAutomaatController(CarController carController){
        this.carController = carController;
    }

    public double getDagOmzet() {
        Car[][][] cars = carController.getAllCars();
        for(Car[][] cars1 : cars)
        {
            for(Car[] cars2 : cars1)
            {
                for(Car car: cars2)
                {
                    try
                    {
                        AdHocCar adHocCar = (AdHocCar)car;
                        if(adHocCar.getColor() == Color.red && carPaying.getIsPaying()) {
                            dagOmzet += adHocCar.getParkingTime() * kostenPM;
                        }
                    }
                    catch (Exception b)
                    {
                        // Do nothing.
                    }
                }
            }
        }
        return dagOmzet;
    }

    public double getVerwachteDagOmzet(){
        double verwachteDagOmzet = 0;

        return checkCars(verwachteDagOmzet);
    }

    public double getWeekOmzet(){
        double weekOmzet = 0;
                                    /*if(timeController.getTime().equals("23:29")) {
                                            dagOmzet = 0;
                                        }*/
        return weekOmzet += getDagOmzet();
    }


    private double checkCars(double input){
        Car[][][] cars = carController.getAllCars();
        for(Car[][] cars1 : cars)
        {
            for(Car[] cars2 : cars1)
            {
                for(Car car: cars2)
                {
                    try
                    {
                        AdHocCar adHocCar = (AdHocCar)car;
                        if(adHocCar.getColor() == Color.red) {
                            input += adHocCar.getParkingTime() * kostenPM;
                        }
                    }
                    catch (Exception b)
                    {
                        // Do nothing.
                    }
                }
            }
        }
        return input;
    }

}
