package Controllers;

import Models.*;

import java.awt.*;
import java.sql.Time;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jop on 24-1-2017.
 */
public class BetaalAutomaatController extends AbstractController {

    private Car carPaying;
    private CarController carController;
    private TimeController timeController;

    private double kostenPM = 0.05;
    private double dagOmzet = 0;
    private double verwachteDagOmzet = 0;
    private double weekOmzet = 0;
    private double maandOmzet = 0;

    private HashMap<Integer, Double> maanden;
    private HashMap<Integer, Double> weken;


    private int called = 0;


    public BetaalAutomaatController() {
        this.carController = (CarController) super.registeryController.getObjectInstance("Controllers.CarController");

        //Connecting to the "Main" TimeController
        timeController = (TimeController) super.registeryController.getObjectInstance("Controllers.TimeController");
        maanden = new HashMap<Integer, Double>(); //<Maandnummer, Omzet (week 1+week2 etc)>
        weken = new HashMap<Integer, Double>(); //<Weeknummer, weekOmzet>
    }

    private double calcDagOmzet() {
        Car[][][] cars = carController.getAllCars();
        for (Car[][] cars1 : cars) {
            for (Car[] cars2 : cars1) {
                for (Car car : cars2) {
                    try {
                        AdHocCar adHocCar = (AdHocCar) car;
                        if (adHocCar.getColor() == Color.red && adHocCar.getMinutesLeft() == 0) {
                            dagOmzet += adHocCar.getParkingTime() * kostenPM;
                        }
                    } catch (Exception b) {
                        // Do nothing.
                    }
                }
            }
        }
        return dagOmzet;
    }

    private double calcVerwachteDagOmzet() {
        double input = 0;
        Car[][][] cars = carController.getAllCars();
        for (Car[][] cars1 : cars) {
            for (Car[] cars2 : cars1) {
                for (Car car : cars2) {
                    try {
                        AdHocCar adHocCar = (AdHocCar) car;
                        if (adHocCar.getColor() == Color.red) {
                            input = adHocCar.getParkingTime() * kostenPM;
                        }
                    } catch (Exception b) {
                        // Do nothing.
                    }
                }
            }
        }
        return verwachteDagOmzet += input;
    }

    private double calcWeekOmzet(){
        weekOmzet = weekOmzet + dagOmzet;
        return weekOmzet;
    }

    private double calcMaandOmzet(){
        double value;
        for(int i = 1; i < 5; i++) {
            value = weken.get(i);
            maandOmzet = maandOmzet + value;
        }
        return maandOmzet;
    }

    public double getDagOmzet() {
        return calcDagOmzet();
    }

    public double getVerwachteDagOmzet() {
        return calcVerwachteDagOmzet();
    }

    public double getWeekOmzet() {
        return weekOmzet;
    }

    public double getMaandOmzet(){
        return maandOmzet;
    }

    public void putMaandOmzet(){
        maanden.put(timeController.getMonth(),getMaandOmzet());
    }

    public void putWeekOmzet() {
        weken.put(timeController.getWeek(), getWeekOmzet());
    }



    public void resetFields() {
        called++;
        if (called == 2) {
            called = 0;
            if (timeController.getWeek() == 4 && timeController.getDay() == 7 && timeController.getTime().equals("23:30")) {
                calcWeekOmzet();
                calcMaandOmzet();
                putMaandOmzet();

                System.out.println(timeController.getDay() + " Omzet: "+dagOmzet);
                System.out.println("weeknr: " + timeController.getWeek() + " Omzet: " + weken.get(timeController.getWeek()));
                System.out.println("Maandnr: " + timeController.getMonth() + " Omzet: " + maanden.get(timeController.getMonth()));

                dagOmzet = 0;
                verwachteDagOmzet = 0;
                weekOmzet = 0;
                maandOmzet = 0;
            } else if (timeController.getDay() == 7 && timeController.getTime().equals("23:30")) {
                calcWeekOmzet();
                putWeekOmzet();

                System.out.println(timeController.getDay() + " Omzet: "+dagOmzet);
                System.out.println("weeknr: " + timeController.getWeek() + " Omzet: " + weken.get(timeController.getWeek()));

                dagOmzet = 0;
                verwachteDagOmzet = 0;
                weekOmzet = 0;
            } else if (timeController.getTime().equals("23:30")) {
                System.out.println(timeController.getDay() + " Omzet: " + dagOmzet);

                calcWeekOmzet();
                dagOmzet = 0;
                verwachteDagOmzet = 0;
            } else {
                //Do nothing!!
            }
        }
    }
}



/*
    public void resetFields(int i){
        switch (i){
            case 1:
                weekOmzet = weekOmzet + dagOmzet;
                putWeekOmzet();
                System.out.println("weeknr: "+weekNummer +" Omzet: "+weken.get(weekNummer));
                dagOmzet = 0;
                verwachteDagOmzet = 0;
                weekOmzet = 0;
                break;
            case 2:
                weekOmzet = weekOmzet + dagOmzet;
                dagOmzet = 0;
                verwachteDagOmzet = 0;
                break;
            //case 3:
        }
    }*/

/*
    Set set = weken.entrySet();
    Iterator iterator = set.iterator();
                while(iterator.hasNext()){
                        Map.Entry mentry = (Map.Entry)iterator.next();
                        System.out.println("Weeknr: "+mentry.getKey() + " omzet = "+mentry.getValue());
                        }

*/