package Models;
import Controllers.CarController;

import java.util.HashMap;

/**
 * Created by Bessel on 1/24/2017.
 */
public class GarageStats {
    // Counters for each type of entity (adhocCar, ParkingPassCar, Que, parkingPassque) in the simulation.
    private HashMap<Class<?>, Counter> counters;
    // Whether the counters are currently up to date.
    private boolean countsValid;

    /**
     * Construct a FieldStats object.
     */
    public GarageStats()
    {
        // Set up a collection for counters for each type of animal that
        // we might find
        counters = new HashMap<Class<?>, Counter>();
        countsValid = true;
    }

    /**
     * Get details of what is in the field.
     * @return A string describing what is in the field.
     */
    public String getPopulationDetails(CarController carController)
    {
        StringBuffer buffer = new StringBuffer();
        if(!countsValid) {
            generateCounts(carController);
        }
        for(Class<?> key : counters.keySet()) {
            Counter info = counters.get(key);
            buffer.append(info.getName().substring(6));
            buffer.append(": ");
            buffer.append(info.getCount());
            buffer.append(' ');
        }
        return buffer.toString();
    }

    /**
     * Invalidate the current set of statistics; reset all
     * counts to zero.
     */

    public void reset()
    {
        countsValid = false;
        for(Class<?> key : counters.keySet()) {
            Counter count = counters.get(key);
            count.reset();
        }
    }
    /**
     * @return Hashmap<Class<?>,Counter>
     */
    public HashMap<Class<?>,Counter> getHashMap()
    {
        return counters;
    }
    /**
     * Increment the count for one class of animal.
     * @param car The class of car to increment.
     */
    public void incrementCount(Class<?> car)
    {
        Counter count = counters.get(car);
        if(count == null) {
            // We do not have a counter for this species yet.
            // Create one.
            count = new Counter(car.getName());
            counters.put(car, count);
        }
        count.increment();
    }

    /**
     * Indicate that an animal count has been completed.
     */
    public void countFinished()
    {
        countsValid = true;
    }

    /**
     * Determine whether the simulation is still viable.
     * I.e., should it continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(CarController carController)
    {
        // How many counts are non-zero.
        int nonZero = 0;
        if(!countsValid) {
            generateCounts(carController);
        }
        for(Class<?> key : counters.keySet()) {
            Counter info = counters.get(key);
            if(info.getCount() > 0) {
                nonZero++;
            }
        }
        return nonZero > 1;
    }

    /**
     * Generate counts of the number of foxes and rabbits.
     * These are not kept up to date as foxes and rabbits
     * are placed in the field, but only when a request
     * is made for the information.
     * @param carController The field to generate the stats for.
     */
    private void generateCounts(CarController carController) {
        reset();
        for (int floor = 0; floor < carController.getNumberOfFloors(); floor++) {
            for (int rows = 0; rows < carController.getNumberOfRows(); rows++) {
                for (int places = 0; places < carController.getNumberOfPlaces(); places++) {
                    Object car = carController.getCarAt(floor, rows, places);
                    if (car != null) {
                        incrementCount(car.getClass());
                    }
                }

            }
            countsValid = true;
        }
    }
}
