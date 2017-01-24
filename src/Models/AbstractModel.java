package Models;

/**
 * Created by Bessel on 1/24/2017.
 */
// import java libraries
import java.util.*;
// import project classes
import Views.*;

public abstract class AbstractModel {
    // instance variables
    private List<AbstractView> views; // create a List with all views in the app
    /**
     * Constructor for the AbstractModel class
     */
    public AbstractModel() {
        views=new ArrayList<AbstractView>();
    }
    /**
     * Add a view to the List so it can be updated
     * @param view
     */
    public void addView(AbstractView view) {
        views.add(view);
    }
    /**
     * Notify the views of any change that has happened
     * Calls the method updateView for every view
     */
    public void notifyViews() {
        for(AbstractView v: views) v.updateView();
    }
}

