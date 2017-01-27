package Controllers;

import Models.*;

/**
 * Created by Jop on 24-1-2017.
 */
public class BetaalAutomaatController {

    private double kostenPM = 0.05;
    private double dagOmzet;
    private double verwachteDagOmzet;
    private double weekOmzet;
    private CarController carController;


    public BetaalAutomaatController(CarController carController){
        this.carController = carController;
    }

    public double getDagOmzet(){

        //Een statement die elke dag hem weer op 0 zet
        //if(car.getIsPaying()) {
         //   dagOmzet = (car.getParkingTime() * kostenPM) + dagOmzet;
        //}
        return dagOmzet;
    }

   // public double getVerwachteDagOmzet(){
   //     return verwachteDagOmzet += (car.getParkingTime() * kostenPM);
  //  }

    public double getWeekOmzet(){
        Car[][][] cars = carController.getAllCars();
        double weekomzet = 0;
        for(Car[][] cars1 : cars)
        {
            for(Car[] cars2 : cars1)
            {
                for(Car car: cars2)
                {

                    try
                    {
                        AdHocCar adHocCar = (AdHocCar)car;
                        weekomzet += adHocCar.getParkingTime() * 0.05;
                    }
                    catch (Exception e)
                    {
                        // Do nothing.
                    }
                }
            }
        }

        return weekomzet;
    }

    //public int getTimeParked(){
    //    return car.getParkingTime();
    //}

}
