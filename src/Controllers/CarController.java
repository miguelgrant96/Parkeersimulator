package Controllers;

import Models.*;

/**
 * Controls the cars in the parking lot
 *
 * @author Marnick
 * @Version 1.6
 * @since 24-01-2017
 */
//Fields
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

    //Constructor
    public CarController(int numberOfFloors, int numberOfRows, int numberOfPlaces){
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;

        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

        adhoc = new Counter("adhoc");
        pass = new Counter("pass");

        passSpots = 56;
        passHolder = 0;
    }

    //Methods

    /**
     * @return The Counter of AdHoc cars
     */
    public Counter getAdhoc(){
        return adhoc;
    }

    /**
     * @return The Counter of ParkingPass cars
     */
    public Counter getPass(){
        return pass;
    }

    /**
     * @return the number of ParkingPass cars
     */
    protected int getPassHolder(){
        return passHolder;
    }

    /**
     * @return The number of floors in the parking lot
     */
    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     * @return The number of rows on each floor
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * @return The number of places on each row
     */
    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    /**
     * @return the number of open spots
     */
    public int getNumberOfOpenSpots(){
        return numberOfOpenSpots;
    }

    /**
     * @return the number of spots that are reserved for ParkingPass cars
     */
    public int getPassSpots() {return passSpots; }


    /**
     * Sets the number of reserved ParkingPass cars
     * @param passSpots number of spots that are reserved for ParkingPass cars
     */
    public void setPassSpots(int passSpots)
    {
        this.passSpots = passSpots;
    }

    /**
     * Gets all cars to calculate the sales volume
     * @return
     */
    protected Car[][][] getAllCars()
    {
        return cars;
    }

    /**
     * get the location where a car can park
     * @param location location where a entered car has to park
     * @return the exact floor, row and spot where to park
     */
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    /**
     * Can be used if you want to get a car from a certain spot
     * @param floor the floor you want the car from
     * @param row the row you want the car from
     * @param places the spot you want the car from
     * @return the car at that spot
     */
    public Car getCarAt(int floor, int row, int places) {

        return cars[floor][row][places];
    }

    /**
     * Sets the car to an exact location
     * @param location Location where a entered car has to park
     * @param car A car that wants to park
     * @return true if there is a valid location, false if not
     */
    protected boolean setCarAt(Location location, Car car) {
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

    /**
     * removes cars from a spot
     * @param   location location where a car leaves his place
     * @return  null if a location is not valid or already empty
     *          car is car that want to leave
     */
    protected Car removeCarAt(Location location) {
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

    /**
     * checks for the first possible spot for a ParkingPass car to park
     * @return The spot where a car can park
     */
    protected Location getFirstPassLocation() {
        if (passHolder < getPassSpots()) {
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

    /**
     * checks for the first possible spot for a AdHoc car to park
     * @return The spot where a car can park
     */
    protected Location getFirstPaidLocation() {
        int paid = 0;
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    paid++;
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        if (paid <= getPassSpots()) {
                            //Do Nothing
                        } else {
                            return location;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Checks the first leaving car in the Parking Lot
     * @return the first leaving car
     */
    protected Car getFirstLeavingCar() {
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

    /**
     *??
     */
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

    /**
     * Checks if the location where a car want to park is valid or not
     * @param location location where a car can park
     * @return true if it is a valid location, false if not
     */
    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }

    /**
     * Used to resed the simulator with an empty Parking Lot
     */
    public void resetCars()
    {
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;

        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

        adhoc = new Counter("adhoc");
        pass = new Counter("pass");

        passSpots = 81;
        passHolder = 0;
    }
}
