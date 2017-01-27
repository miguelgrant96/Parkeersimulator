package Models;

/**
 * Created by Bessel on 1/23/2017.
 */
public class Counter {
    // A name for this type of simulation participant
    private String name;
    // How many of this type exist in the simulation.
    private int count;

    /**
     * Provide a name for one of the simulation types.
     * @param name  Bijv. Abonnementhouders
     */
    public Counter(String name)
    {
        this.name = name;
        count = 0;
    }

    /**
     * Get the name
     * @return The short description of this type.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get the count
     * @return The current count for this type.
     */
    public int getCount()
    {
        return count;
    }

    /**
     * Increment the current count by one.
     */
    public void increment()
    {
        count++;
    }

    /**
     * Reset the current count to zero.
     */
    public void reset()
    {
        count = 0;
    }
}
