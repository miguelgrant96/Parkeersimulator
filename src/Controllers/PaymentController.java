package Controllers;

import Models.*;
import java.awt.*;
import java.util.HashMap;

/**
 * calculates and stores the profits
 *
 * @author Jop Bakker
 * @version 1.5
 * @since 25-01-2016
 */

public class PaymentController extends AbstractController {

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


    /**
     * Constructor of the PaymentController class.
     * connecting the requirred controllers and creating the needed HashMaps
     */
    public PaymentController() {
        this.carController = (CarController) super.registeryController.getObjectInstance("CarController");

        //Connecting to the "Main" TimeController
        timeController = (TimeController) super.registeryController.getObjectInstance("TimeController");

        maanden = new HashMap<>(); //<Maandnummer, Omzet (week1 + week2 etc)>
        weken = new HashMap<>(); //<Weeknummer, weekOmzet>
    }

    /**
     * Method to calculate the daily profit
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
     * Method to calculate the expected daily profit
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
     * Method to calculate the weekly profit
     * @return return the value of weekOmzet
     */
    private double calcWeekOmzet(){
        weekOmzet = weekOmzet + dagOmzet;
        return weekOmzet;
    }

    /**
     * Method to calculate the monthly profit
     * @return return the value of maandOmzet
     */
    private double calcMaandOmzet(){
        double value;
        for(int i = 1; i <= 4; i++) {
            value = weken.get(i);
            maandOmzet = (maandOmzet + value);
        }
        maandOmzet = maandOmzet + (carController.getPassSpots() * 150);
        return maandOmzet;
    }

    /**
     *
     * @return the HashMap maanden
     */
    private HashMap<Integer, Double> getMaanden() {
        return maanden;
    }

    /**
     *
     * @return the HashMap weken
     */
    private HashMap<Integer, Double> getWeken() {
        return weken;
    }

    /**
     *
     * @param maandNummer Integer to choose which maand is called
     * @return return the value of maanden[maandNummer]
     */
    public double getFromMaanden(int maandNummer){
        return maanden.get(maandNummer);
    }

    /**
     * Method to remove all data in HashMap maanden
     */
    public void clearMaanden(){
        maanden.clear();
    }

    /**
     *
     * @param weekNummer Integer to choose which week is called
     * @return return the value of weken[weekNummer]
     */
    public double getFromWeken(int weekNummer){
        return weken.get(weekNummer);
    }

    /**
     * Method to remove all data in HashMap weken
     */
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
    private double getMaandOmzet(){
        return maandOmzet;
    }

    /**
     * Method to add data to HashMap maanden
     */
    private void putMaandOmzet(){
        maanden.put(timeController.getMonth(),getMaandOmzet());
    }

    /**
     * Method to add data to HashMap weken
     */
    private void putWeekOmzet() {
        weken.put(timeController.getWeek(), getWeekOmzet());
    }


    /**
     * Method to save the profit fields
     */
    public void saveDataToMap() {
        called++;
        String resetTime = "23:50";
            if(timeController.getMonth() == 12 && timeController.getWeek() == 4 && timeController.getDay() == 7 && timeController.getTime().equals(resetTime)
                || timeController.getWeek() == 4 && timeController.getDay() == 7 && timeController.getTime().equals(resetTime)){
                calcWeekOmzet();
                putWeekOmzet();
                calcMaandOmzet();
                putMaandOmzet();

                resetFields("Month");

            } else if (timeController.getDay() == 7 && timeController.getTime().equals(resetTime)) {
                calcWeekOmzet();
                putWeekOmzet();

                resetFields("Week");

            } else if (timeController.getTime().equals(resetTime)) {
                calcWeekOmzet();

                resetFields("Day");
            } else {
                //Do nothing!!
            }
        }

    /**
     *
     * Method to reset values according to the giving @param
     * @param reset String value to choose which case is used
     */
    private void resetFields(String reset){
        switch(reset){
            case "Month":
                maandOmzet = 0;
            case "Week":
                weekOmzet = 0;
            case "Day":
                dagOmzet = 0;
                verwachteDagOmzet = 0;
                break;
            default:
                System.out.println("Error resetting PaymentController values");
                break;
        }
    }

    public void resetPayments()
    {
        dagOmzet = 0;
        verwachteDagOmzet = 0;
        weekOmzet = 0;
        maandOmzet = 0;
    }
}