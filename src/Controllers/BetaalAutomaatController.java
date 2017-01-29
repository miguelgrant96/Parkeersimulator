package Controllers;

import Models.*;

import java.awt.*;

/**
 * Created by Jop on 24-1-2017.
 */
public class BetaalAutomaatController {

    private Car carPaying;
    private CarController carController;

    private double kostenPM = 0.05;


    public BetaalAutomaatController(CarController carController){
        this.carController = carController;
    }

    public double getDagOmzet() {
        double dagOmzet = 1;
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

        return test(verwachteDagOmzet);
    }

    public double getWeekOmzet(){
        double weekOmzet = 0;

        return weekOmzet += getDagOmzet();
    }


    private double test(double e){
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
                            e += adHocCar.getParkingTime() * kostenPM;
                        }
                    }
                    catch (Exception b)
                    {
                        // Do nothing.
                    }
                }
            }
        }
        return e;
    }

}
