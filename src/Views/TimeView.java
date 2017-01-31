package Views;

import Controllers.SimulatorController;
import Controllers.TimeController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jop on 27-1-2017.
 */
public class TimeView extends AbstractView {

    private TimeController timeController;
    private SimulatorController simulatorController;
    private JLabel day,time,empty;
    private JPanel dayTime;
    private String dayString;

    public TimeView(){
        // Connecting with the same TimeControllers the other classes in the project use.
        timeController = (TimeController) super.registeryController.getObjectInstance("Controllers.TimeController");

        // Getting the current day
        dayString = "Day: "+timeController.getDay();


        setSize(250, 50);
        setLayout(new GridLayout(0,2));


        dayTime = new JPanel(new GridLayout(0,1));
        day = new JLabel(dayString);
        time = new JLabel(timeController.getTime());

        dayTime.add(day);
        dayTime.add(time);

        //Adding an empty label in order to get the DayTime panel in the center of the screen
        empty = new JLabel();

        add(empty);
        add(dayTime);

        setVisible(true);

    }

    public void updateView(){
        //Updating the Time and Day with every Tick()
        day.setText(this.toString());
        time.setText(timeController.getTime());
    }
    @Override
    public String toString(){
        //Overriding the toString() method to het an updatable Day
        return dayString = "Day: "+timeController.getDay();
    }
}
