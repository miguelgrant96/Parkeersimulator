package Controllers;

import Models.*;

/**
 * Created by Bessel on 1/24/2017.
 */

public class CarController extends AbstractController {
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;
    private Counter adhoc;
    private Counter pass;
    private int passSpots;
    private int passHolder;

    public CarController(int numberOfFloors, int numberOfRows, int numberOfPlaces){
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;

        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

        adhoc = new Counter("adhoc");
        pass = new Counter("pass");

        passSpots = 81;
        passHolder = 0;
    }
    public Counter getAdhoc(){
        return adhoc;
    }

    public Counter getPass(){
        return pass;
    }

    public int getPassHolder(){
        return passHolder;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public int getNumberOfOpenSpots(){
        return numberOfOpenSpots;
    }

    public int getPassSpots() {return passSpots; }

    public Car[][][] getAllCars()
    {
        return cars;
    }

    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public Car getCarAt(int floor, int row, int places) {

        return cars[floor][row][places];
    }

    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;

            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        if (car instanceof ParkingPassCar) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
            car.setLocation(null);
            numberOfOpenSpots++;
            pass.decrement();
            passHolder--;
            return car;
        }
        else if(car instanceof Reservation)
        {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
            numberOfOpenSpots++;
            return car;
        }
        else{
            cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
            car.setLocation(null);
            numberOfOpenSpots++;
            adhoc.decrement();
            return car;

        }
    }

    public Location getFirstFreeLocation() {
        while (passHolder < getPassSpots()) {
            for (int floor = 0; floor < getNumberOfFloors(); floor++) {
                for (int row = 0; row < getNumberOfRows(); row++) {
                    for (int place = 0; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        if (getCarAt(location) == null) {
                            passHolder++;
                            return location;
                        }
                    }
                }
            }
        }
        return null;
    }

    public Location getFirstPaidLocation() {
        int paid = 0;
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    paid++;
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        if (paid <= getPassSpots()) {
                        } else {
                            return location;
                        }
                    }
                }
            }
        }
        return null;
    }

    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    public void tick() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }

}
