package Controllers;

import Models.*;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Controls the CarQueue of the parking lot
 *
 * @author Marnick
 * @Version 1.4
 * @since 23-01-2017
 */

//Fields
public class CarQueueController extends AbstractController {

    private SimulatorController simulatorController;
    private TimeController timeController;
    private CarController carController;

    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;

    private int carsLeft;
    private int numberOfCarsInQueue;
    private int carsToday;
    private int leftCarsToday;


    int weekDayArrivals; // average number of arriving cars per hour
    int weekendArrivals; // average number of arriving cars per hour
    int weekDayPassArrivals; // average number of arriving cars per hour
    int weekendPassArrivals; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute

    private static final String AD_HOC = "1";
    private static final String PASS = "2";

    //Constructor
    public CarQueueController() {

        this.simulatorController = (SimulatorController) super.registeryController.getObjectInstance("SimulatorController");
        this.carController = (CarController) super.registeryController.getObjectInstance("CarController");
        this.timeController = (TimeController) super.registeryController.getObjectInstance("TimeController");

        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();

        setArrivals();

        carsLeft = 0;
        numberOfCarsInQueue = 0;
        carsToday = 0;
        leftCarsToday = 0;

    }

    //Methods

    /**
     * Set the number of arriving cars every hour between certain times
     */
    protected void setArrivals() {
        //Not busy
        if (timeController.getHour() >= 22 && timeController.getHour() <= 5) {

            weekDayArrivals = 10; // average number of arriving cars per hour
            weekDayPassArrivals = 1; // average number of arriving cars per hour
            weekendArrivals = 15; // average number of arriving cars per hour
            weekendPassArrivals = 1; // average number of arriving cars per hour

            //A little busy
        } else if (timeController.getHour() < 7 ||
                timeController.getHour() > 9 && timeController.getHour() < 12 ||
                timeController.getHour() > 14 && timeController.getHour() < 17 ||
                timeController.getHour() > 19 && timeController.getHour() < 22) {

            weekDayArrivals = 25; // average number of arriving cars per hour
            weekDayPassArrivals = 10; // average number of arriving cars per hour
            weekendArrivals = 100; // average number of arriving cars per hour
            weekendPassArrivals = 5; // average number of arriving cars per hour

            // Very Busy
        } else {
            weekDayArrivals = 100; // average number of arriving cars per hour
            weekDayPassArrivals = 50; // average number of arriving cars per hour
            weekendArrivals = 200; // average number of arriving cars per hour
            weekendPassArrivals = 5; // average number of arriving cars per hour
        }
    }

    /**
     * Set the required method needed for the incoming cars
     */
    protected void handleEntrance() {
        carsArriving();
        setArrivals();
        carsLeavingQueue(entranceCarQueue);
        carsEntering(entrancePassQueue);
        carsEntering(entranceCarQueue);
    }


    /**
     * Set the required method needed for the exiting cars
     */
    protected void handleExit() {
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    /**
     * Determines if a car has to pay or not, if it has, it is added to the payment queue
     */
    private void carsReadyToLeave() {
        // Add leaving cars to the payment queue.
        Car car = carController.getFirstLeavingCar();
        while (car != null) {
            if (car.getHasToPay()) {
                car.setIsPaying(true);
                paymentCarQueue.addCar(car);
            } else {
                carLeavesSpot(car);
            }
            car = carController.getFirstLeavingCar();
        }
    }

    /**
     * Check the average number of cars every hour and every minute
     *
     * @param weekDay checks the day if it is a weekday
     * @param weekend checks the day if it is in the weekend
     * @return the number of cars every minute
     */
    private int getNumberOfCars(int weekDay, int weekend) {
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = timeController.getDay() < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int) Math.round(numberOfCarsPerHour / 60);
    }

    /**
     * Let the cars pay
     */
    private void carsPaying() {
        int i = 0;
        while (paymentCarQueue.carsInQueue() > 0 && i < paymentSpeed) {
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
        }
    }

    /**
     * Let the cars leave
     */
    private void carsLeaving() {
        int i = 0;
        while (exitCarQueue.carsInQueue() > 0 && i < exitSpeed) {
            exitCarQueue.removeCar();
            i++;
            leftCarsToday++;
        }
    }

    /**
     * removes the car from a spot and adds it to a queue of exiting cars
     *
     * @param car a car in the parkinglot
     */
    private void carLeavesSpot(Car car) {
        carController.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }


    /**
     * Add the arriving cars to a queue where it has to wait till it can enter
     *
     * @param numberOfCars the number of cars in the parkinglot
     * @param type         the type of the cars (pass holders or non passholders)
     */
    private void addArrivingCars(int numberOfCars, String type) {
        // Add the cars to the back of the queue.
        switch (type) {
            case AD_HOC:
                for (int i = 0; i < numberOfCars; i++) {
                    entranceCarQueue.addCar(new AdHocCar());
                }
                break;
            case PASS:
                for (int i = 0; i < numberOfCars; i++) {
                    if (carController.getPassHolder() < carController.getPassSpots()) {
                        entrancePassQueue.addCar(new ParkingPassCar());
                    }
                }
                break;
        }
    }

    /**
     * Add the cars to the numberOfCars total of each type of car
     */
    private void carsArriving() {
        int numberOfCars = getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);

        numberOfCars = getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);
    }

    /**
     * Remove car from the front of the queue and assign to a parking space.
     * It divides the cars between passholders and non passholders
     *
     * @param queue the queue of waiting cars till they can enter the parking lot
     */
    private void carsEntering(CarQueue queue) {
        int i = 0;
        while (queue.carsInQueue() > 0 &&
                carController.getNumberOfOpenSpots() > 0 &&
                i < enterSpeed) {
            Car car = queue.removeCar();
            if (car.getColor() == Color.blue) {
                Location freeLocation = carController.getFirstPassLocation();
                carController.setCarAt(freeLocation, car);
                carController.getPass().increment();
            } else {
                Location freeLocation = carController.getFirstPaidLocation();
                carController.setCarAt(freeLocation, car);
                carController.getAdhoc().increment();
            }
            i++;
            carsToday++;
        }
    }

    /**
     * Let cars random leave the queue if they don't want to wait too long.
     * The more cars in the queue the bigger the chance that they leave
     *
     * @param queue The queue of waiting cars till they can enter the parking lot
     */
    private void carsLeavingQueue(CarQueue queue) {
        numberOfCarsInQueue = queue.carsInQueue();
        int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        if (queue.carsInQueue() >= 20) {
            if (randomNum > 1) {
                carsLeft++;
                carLeavingQueue();
            }
        } else if (queue.carsInQueue() >= 15) {
            if (randomNum > 3) {
                carsLeft++;
                carLeavingQueue();
            }
        } else if (queue.carsInQueue() >= 10) {
            if (randomNum > 5) {
                carsLeft++;
                carLeavingQueue();
            }
        } else if (queue.carsInQueue() >= 5) {
            if (randomNum > 8) {
                carsLeft++;
                carLeavingQueue();
            }
        } else if (queue.carsInQueue() >= 3) {
            if (randomNum > 9) {
                carsLeft++;
                carLeavingQueue();
            }
        }
    }

    /**
     * @return The number of current cars waiting in the queue
     */
    public int getWaitingCars() {
        return numberOfCarsInQueue;
    }

    /**
     * @return The number of cars that have left the queue
     */
    public int getLeftCars() {
        return carsLeft;
    }

    /**
     * @return The number of cars that have entered the Parking Lot today
     */
    public int getCarsToday() {
        return carsToday;
    }

    /**
     * @return The number of cars that have left the Parking Lot today
     */
    public int getLeavingCarsToday() {
        return leftCarsToday;
    }

    /**
     * This method is used to reset the counter of left cars, entered cars, waiting cars and left cars.
     * This is used every day at 0:00
     */
    public void resetCars() {
        carsLeft = 0;
        carsToday = 0;
    }

    /**
     * When a car wants to leave the queue, the car gets removed from the queue
     */
    private void carLeavingQueue() {
        entranceCarQueue.removeCar();
    }

    /**
     * The queue of cars is resetted
     */
    public void resetQueue() {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        setArrivals();
        resetCars();
    }
}
