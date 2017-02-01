package Controllers;

import Models.*;

import java.awt.*;
import java.sql.Time;

/**
 * Created by Jop on 24-1-2017.
 */
public class BetaalAutomaatController extends AbstractController{

    private Car carPaying;
    private CarController carController;

    private double kostenPM = 0.05;
    private double dagOmzet = 0;
    private double verwachteDagOmzet = 0;
    private double weekOmzet = 0;


    public BetaalAutomaatController(){
        this.carController = (CarController) super.registeryController.getObjectInstance("CarController");
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
                        if(adHocCar.getColor() == Color.red && adHocCar.getMinutesLeft() == 0) {
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
        double input = 0;
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
                            input = adHocCar.getParkingTime() * kostenPM;
                        }
                    }
                    catch (Exception b)
                    {
                        // Do nothing.
                    }
                }
            }
        }
        return verwachteDagOmzet += input;
    }

    public double getWeekOmzet(){
        return weekOmzet;
    }


    public void resetFields(int i){
        switch (i){
            case 1:
                dagOmzet = 0;
                verwachteDagOmzet = 0;
                weekOmzet = 0;
                break;

            case 2:
                weekOmzet = weekOmzet + dagOmzet;
                dagOmzet = 0;
                verwachteDagOmzet = 0;
                break;
        }
    }
}
