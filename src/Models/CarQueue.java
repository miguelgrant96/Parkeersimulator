package Models;

import Models.Car;

import java.util.LinkedList;
import java.util.Queue;

public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();
    private Queue<Car> passQueue = new LinkedList<>();

    /**
     * Deze werkt
     * Voegt auto toe aan de que
     * @param car
     * @return car wordt toegevoegd aan de queue
     */
    public boolean addCar(Car car) {
        return queue.add(car);
    }

    /**
     * Deze misschien later gebruiken als de queues wel gebruikt worden
     * @return
     */
    /*public boolean addCar(Car car) {
        if(car instanceof ParkingPassCar){
            return passQueue.add(car);
            } else {
            return queue.add(car);
            }
        }*/


    /**
     * @return Verwijderd auto uit de queue
     */
    public Car removeCar() {
        return queue.poll();
    }

    /**
     * Deze als passqueue gebruikt wordt
     * @return verwijderd auto uit de passQueue
     */
    /*public Car removePassCar() {
        return passQueue.poll();
    }*/

    /**
     * @return het aantal autos in de queue
     */
    public int carsInQueue(){
    	return queue.size();
    }

    /**
     * Deze als passQueue gebruikt wordt
     * @return aantal auto's in de passQueue
     */
    public int carsInPassQueue(){
        return passQueue.size();
    }


}
