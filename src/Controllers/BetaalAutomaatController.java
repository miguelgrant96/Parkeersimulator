package Controllers;

import Models.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by Jop on 24-1-2017.
 */
public class BetaalAutomaatController extends AbstractController {

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

        maanden = new HashMap<>(); //<Maandnummer, Omzet (week1 + week2 etc)>
        weken = new HashMap<>(); //<Weeknummer, weekOmzet>
    }

    /**
     *
     * @return return the value of dagOmzet
     */
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

    /**
     *
     * @return return the value of verwachteDagOmzet
     */
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

    /**
     *
     * @return return the value of weekOmzet
     */
    private double calcWeekOmzet(){
        weekOmzet = weekOmzet + dagOmzet;
        return weekOmzet;
    }

    /**
     *
     * @return return the value of maandOmzet
     */
    private double calcMaandOmzet(){
        double value;
        for(int i = 1; i <= 4; i++) {
            value = weken.get(i);
            maandOmzet = maandOmzet + value;
        }
        return maandOmzet;
    }

    /**
     *
     * @return the HashMap maanden
     */
    public HashMap<Integer, Double> getMaanden() {
        return maanden;
    }

    /**
     *
     * @return the HashMap weken
     */
    public HashMap<Integer, Double> getWeken() {
        return weken;
    }

    /**
     *
     * @param maandNummer
     * @return return the value of maanden[maandNummer]
     */
    public double getFromMaanden(int maandNummer){
        return maanden.get(maandNummer);
    }

    public void clearMaanden(){
        maanden.clear();
    }

    /**
     *
     * @param weekNummer
     * @return return the value of weken[weekNummer]
     */
    public double getFromWeken(int weekNummer){
        return weken.get(weekNummer);
    }

    public void clearWeken(){
        weken.clear();
    }

    /**
     *
     * @return return the value of calcDagOmzet()
     */
    public double getDagOmzet() {
        return calcDagOmzet();
    }

    /**
     *
     * @return return the value of calcVerwachteDagOmzet
     */
    public double getVerwachteDagOmzet() {
        return calcVerwachteDagOmzet();
    }

    /**
     *
     * @return return the value of weekOmzet
     */
    public double getWeekOmzet() {
        return weekOmzet;
    }

    /**
     *
     * @return return the value of maandOmzet
     */
    public double getMaandOmzet(){
        return maandOmzet;
    }

    /**
     *
     */
    public void putMaandOmzet(){
        maanden.put(timeController.getMonth(),getMaandOmzet());
    }

    public void putWeekOmzet() {
        weken.put(timeController.getWeek(), getWeekOmzet());
    }


    /**
     * Method to save the profit fields and when needed reset them
     */
    public void doStuff() {
        called++;
        String resetTime = "00:00";
        if (called == 2) {
            called = 0;
            if(timeController.getMonth() == 12 && timeController.getWeek() == 4 && timeController.getDay() == 7 && timeController.getTime().equals(resetTime)) {
                calcWeekOmzet();
                putWeekOmzet();
                calcMaandOmzet();
                putMaandOmzet();

                dagOmzet = 0;
                verwachteDagOmzet = 0;
                weekOmzet = 0;
                maandOmzet = 0;
            }
            else if (timeController.getWeek() == 4 && timeController.getDay() == 7 && timeController.getTime().equals(resetTime)) {
                calcWeekOmzet();
                putWeekOmzet();
                calcMaandOmzet();
                putMaandOmzet();


                System.out.println("weeknr: " + timeController.getWeek() + " Omzet: " + weken.get(timeController.getWeek()));
                System.out.println("Maandnr: " + timeController.getMonth() + " Omzet: " + maanden.get(timeController.getMonth()));

                dagOmzet = 0;
                verwachteDagOmzet = 0;
                weekOmzet = 0;
                maandOmzet = 0;
            } else if (timeController.getDay() == 7 && timeController.getTime().equals(resetTime)) {
                calcWeekOmzet();
                putWeekOmzet();

                System.out.println("weeknr: " + timeController.getWeek() + " Omzet: " + weken.get(timeController.getWeek()));

                dagOmzet = 0;
                verwachteDagOmzet = 0;
                weekOmzet = 0;
            } else if (timeController.getTime().equals(resetTime)) {
                calcWeekOmzet();
                dagOmzet = 0;
                verwachteDagOmzet = 0;
            } else {
                //Do nothing!!
            }
        }
    }
}