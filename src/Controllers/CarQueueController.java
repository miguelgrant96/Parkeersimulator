package Controllers;

import Models.*;

import java.awt.*;
import java.util.Random;

/**
 * Created by Arjen on 23-1-2017.
 */
public class CarQueueController extends AbstractController{

    private SimulatorController simulatorController;

    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private TimeController timeController;
    private CarController carController;

    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute

    private static final String AD_HOC = "1";
    private static final String PASS = "2";

    public CarQueueController(SimulatorController simulatorController) {

        this.simulatorController = simulatorController;
        carController = simulatorController.getCarController();
        timeController = simulatorController.getTimeController();
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
    }

    public void test(){
        System.out.println(timeController.getTime());
    }

    public void handleEntrance(){
        carsArriving();
        test();
        carsEntering(entrancePassQueue);
        carsEntering(entranceCarQueue);
    }

    public void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    public void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = carController.getFirstLeavingCar();
        while (car!=null) {
            if (car.getHasToPay()){
                car.setIsPaying(true);
                paymentCarQueue.addCar(car);
            }
            else {
                carLeavesSpot(car);
            }
            car = carController.getFirstLeavingCar();
        }
    }

    public int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = timeController.getDay() < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);
    }

    private void carsPaying(){
        // Let cars pay.
        int i=0;
        while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
        }
    }

    private void carsLeaving(){
        // Let cars leave.
        int i=0;
        while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
        }
    }

    private void carLeavesSpot(Car car){
        carController.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }



    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
        switch(type) {
            case AD_HOC:
                for (int i = 0; i < numberOfCars; i++) {
                    entranceCarQueue.addCar(new AdHocCar());
                }
                break;
            case PASS:
                for (int i = 0; i < numberOfCars; i++) {
                    entrancePassQueue.addCar(new ParkingPassCar());
                }
                break;
        }
    }

    private void carsArriving(){
        int numberOfCars= getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);

        numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);
    }

    private void carsEntering(CarQueue queue) {
        int i = 0;
        // Remove car from the front of the queue and assign to a parking space.
        while (queue.carsInQueue() > 0 &&
                carController.getNumberOfOpenSpots() > 0 &&
                i < enterSpeed) {
            Car car = queue.removeCar();
            if (car.getColor() == Color.blue) {
                Location freeLocation = carController.getFirstFreeLocation();
                carController.setCarAt(freeLocation, car);
            } else {
                Location freeLocation = carController.getFirstPaidLocation();
                carController.setCarAt(freeLocation, car);
            }
            i++;
        }
    }
}